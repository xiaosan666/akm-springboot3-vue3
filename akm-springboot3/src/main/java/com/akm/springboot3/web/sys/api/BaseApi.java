package com.akm.springboot3.web.sys.api;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.annotation.ApiFreqLimit;
import com.akm.springboot3.core.annotation.IgnoreResultHandler;
import com.akm.springboot3.core.annotation.IgnoreSysLog;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.enums.ClientTypeEnum;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.*;
import com.akm.springboot3.web.sys.domain.CacheUser;
import com.akm.springboot3.web.sys.domain.LoginWrapper;
import com.akm.springboot3.web.sys.domain.RolePlayWrapper;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.entity.SysUser;
import com.akm.springboot3.web.sys.service.SysRoleService;
import com.akm.springboot3.web.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.akm.springboot3.core.utils.UserThreadUtils.ROLE_TENANT_ADMIN_CODE;


@Tag(name = "sys-Base Api")
@RestController
@RequestMapping("/auth")
@Slf4j
public class BaseApi {

    /**
     * 登录错误锁定时长,单位分钟
     */
    private static final long MINUTE = 30;

    /**
     * 登录密码最大错误次数
     */
    private static final long MAX_ERRORS = 5;


    /**
     * 是否正式环境，正式环境给前端抛出的异常不含堆栈信息
     */
    private final String activeProfile;

    private final AkmConfig akmConfig;
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    BaseApi(Environment env, AkmConfig akmConfig, SysUserService sysUserService, SysRoleService sysRoleService) {
        this.activeProfile = env.getActiveProfiles()[0];
        this.akmConfig = akmConfig;
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }

    @Operation(summary = "获取系统配置", description = "获取系统时间、秘钥、白名单等")
    @PostMapping("/open/config")
    public Dict config() {
        // 从缓存取公钥1和私钥2给前端
        // 前端用公钥1加密，后端用私钥1解密；后端用公钥2加密，前端用私钥1解密
        // 最终返回给前端的公钥1和私钥2用aes加密
        Map<String, String> cacheMap = CacheUtils.get(RedisKeys.RSA_KEY_PAIR);
        String publicKey1 = cacheMap.get("publicKey1");
        String privateKey2 = cacheMap.get("privateKey2");

        // 随机数
        String random = IdUtil.simpleUUID();
        // 时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        // 随机数的后16-lastNumber位 + 时间戳的后lastNumber位每个字符作为随机数的下标取字符 = 长度为16的AES Key
        // 获取时间戳最后一个字符
        String aesKey = getString(timestamp, random);
        // 加密白名单
        Map<String, Object> map = new HashMap<>(16);
        map.put("activeProfile", activeProfile);
        map.put("publicKey", publicKey1);
        map.put("privateKey", privateKey2);
        map.put("enabledEncrypt", akmConfig.getEnabledEncrypt());
        AkmConfig.Login4a login4a = akmConfig.getLogin4a();
        map.put("enabled4aLogin", login4a.getEnable());
        map.put("akm4aServerUrl", login4a.getAkm4aServerUrl());
        if (Boolean.TRUE.equals(akmConfig.getEnabledEncrypt())) {
            map.put("encryptExcludeUris", ArrayUtil.merge(akmConfig.getEncryptExcludeUrls(), akmConfig.getAlwaysExcludeUrls()));
        }
        map.put("enabledSign", akmConfig.getEnabledSign());
        if (Boolean.TRUE.equals(akmConfig.getEnabledSign())) {
            map.put("signExcludeUris", ArrayUtil.merge(akmConfig.getSignExcludeUrls(), akmConfig.getAlwaysExcludeUrls()));
        }
        return Dict.create()
            .set("config", EncryptUtils.aesEncrypt(JSONUtil.toJsonStr(map), aesKey))
            .set("random", random)
            .set("timestamp", timestamp);
    }

    @NotNull
    private static String getString(String timestamp, String random) {
        int lastNumber = Integer.parseInt(timestamp.substring(timestamp.length() - 1));
        if (lastNumber == 0) {
            lastNumber = 10;
        }
        // 时间戳最后一个字符是几就取时间后几位
        String[] timeChars = timestamp.substring(timestamp.length() - lastNumber).split("");
        String[] randomChars = random.split("");
        StringBuilder aesKeySuffix = new StringBuilder();
        for (String t : timeChars) {
            aesKeySuffix.append(randomChars[Integer.parseInt(t)]);
        }
        // 随机数后几位
        String aesKeyPrefix = random.substring(random.length() - 16 + lastNumber);
        return aesKeyPrefix + aesKeySuffix;
    }

    @IgnoreSysLog
    @Operation(summary = "获取图形验证码")
    @PostMapping("/open/captcha")
    public Dict captcha() {
        String codeKey = IdUtil.simpleUUID();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(72, 36, 4, 1);
        String code = captcha.getCode();
        // 缓存验证码，5分钟有效
        StringCacheUtils.set(RedisKeys.CAPTCHA.concat(codeKey), code, 300);
        return Dict.create()
            .set("imgBase64", "data:image/png;base64," + captcha.getImageBase64())
            .set("codeKey", codeKey);
    }

    @Operation(summary = "登陆")
    @PostMapping("/open/login")
    public String login(@RequestBody @Validated LoginWrapper login) {
        // 检查验证码
        if (Boolean.TRUE.equals(akmConfig.getEnableLoginCaptcha())) {
            this.checkCaptcha(login);
        }

        String username = login.getUsername();
        String password = login.getPassword();
        String clientType = login.getClientType();

        // 检查密码错误次数
        this.checkLoginErrors(username);

        SysUser user = sysUserService.selectOneByUsername(username);
        if (user == null) {
            this.setLoginErrorAndThrowException(username);
            return null;
        }

        // 判断密码是否正确
        String p = EncryptUtils.getPassword(password, user.getSalt(), user.getId());
        if (!p.equals(user.getPassword())) {
            this.setLoginErrorAndThrowException(username);
        }
        // 密码正确清除错误次数缓存
        StringCacheUtils.del(RedisKeys.PASSWORD_ERRORS.concat(username));

        AssertUtils.isTrue(user.getEnable() == 1, "账号已被禁用");
        AssertUtils.isTrue(user.getDelFlag() == 0, "账号已失效");
        if (user.getExpiredTime() != null) {
            AssertUtils.isTrue(new Date().compareTo(user.getExpiredTime()) < 0, "账号已过期");
        }

        if (Boolean.TRUE.equals(akmConfig.getForcePasswordChange())) {
            // 首次登录或密码被管理员重置，强制修改密码
            AssertUtils.notNull(user.getLastPasswordChangeTime(), CodeMsg.MUST_CHANGE_PASSWORD);
        }
        if (akmConfig.getPasswordEffectiveDays() != -1) {
            // 密码长时间未修改，强制修改密码
            long betweenDay = DateUtil.between(user.getLastPasswordChangeTime(), DateUtil.date(), DateUnit.DAY);
            AssertUtils.isTrue(betweenDay < akmConfig.getPasswordEffectiveDays(), CodeMsg.PASSWORD_EXPIRED);
        }

        // 清除敏感数据
        user.setPassword(null);
        user.setSalt(null);

        // 更新账号过期时间
        sysUserService.updateExpiredTimeByUserId(user.getId());
        return getToken(user, clientType, true);
    }

    private String getToken(SysUser user, String clientType, boolean deleteOldToken) {
        // 用户id
        String userId = user.getId();

        // userId与token关系key；注：以下concat顺序不能改
        String userIdTokenKey = RedisKeys.USER_ID_TOKEN.concat(userId).concat("_").concat(clientType);

        // 用户登陆或重新登陆，删除已存在的缓存数据
        if (deleteOldToken) {
            String oldToken = StringCacheUtils.get(userIdTokenKey);
            if (oldToken != null) {
                CacheUtils.del(RedisKeys.TOKEN.concat(oldToken));
            }
        }

        // 创建新token
        String token = IdUtil.simpleUUID();
        // token有效期
        long tokenTimeout = getTokenTimeout(clientType);

        // 获取登陆用户所拥有的角色（角色列表倒序），用于鉴权
        List<SysRoleBaseInfo> roleList = sysRoleService.findRoleByUser(userId);

        CacheUser cacheUser = new CacheUser();
        cacheUser.setToken(token);
        cacheUser.setTenantId(user.getTenantId());
        cacheUser.setClientType(clientType);
        cacheUser.setUserId(userId);
        cacheUser.setUsername(user.getUsername());
        cacheUser.setRealname(user.getRealname());
        cacheUser.setTenantCode(user.getTenantCode());
        cacheUser.setOrgId(user.getOrgId());
        cacheUser.setRoleList(roleList);

        // 缓存token与用户信息
        CacheUtils.set(RedisKeys.TOKEN.concat(token), cacheUser, tokenTimeout);
        // 缓存userId和token关系，根据userId可以找到token，找到token用于删除token与用户信息的缓存
        StringCacheUtils.set(userIdTokenKey, token, tokenTimeout);
        // 返回token
        return token;
    }

    /**
     * 检查验证码
     */
    private void checkCaptcha(LoginWrapper login) {
        String code = login.getCode();
        String codeKey = login.getCodeKey();
        AssertUtils.notBlank(code, "验证码不允许为空");
        AssertUtils.notBlank(codeKey, "请获取验证码");
        String cacheCode = StringCacheUtils.get(RedisKeys.CAPTCHA.concat(codeKey));
        AssertUtils.notBlank(codeKey, "验证码已失效，请重新获取");
        AssertUtils.isTrue(code.equalsIgnoreCase(cacheCode), "验证码不正确");
        // 删除用过的验证码
        StringCacheUtils.del(RedisKeys.CAPTCHA.concat(codeKey));
    }

    /**
     * 检查登录错误次数
     */
    private void checkLoginErrors(String username) {
        String cacheKey = RedisKeys.PASSWORD_ERRORS.concat(username);
        String value = StringCacheUtils.get(cacheKey);
        if (value != null && Integer.parseInt(value) >= MAX_ERRORS) {
            throw new BusinessException(CharSequenceUtil.format("您的账号被锁定{}分钟，请稍候再试", 30));
        }
    }

    /**
     * 记录登录错误次数并抛出异常
     */
    private void setLoginErrorAndThrowException(String username) {
        String cacheKey = RedisKeys.PASSWORD_ERRORS.concat(username);
        String value = StringCacheUtils.get(cacheKey);
        int errors = value == null ? 1 : Integer.parseInt(value) + 1;
        StringCacheUtils.set(cacheKey, String.valueOf(errors), 60 * MINUTE);
        String errMsg = CharSequenceUtil.format("用户名或密码已错误{}次，达到{}次将锁定账号{}分钟", errors, MAX_ERRORS, MINUTE);
        this.checkLoginErrors(username);
        throw new BusinessException(errMsg);
    }

    @Operation(summary = "退出登陆")
    @PostMapping("/open/logout")
    public void logout() {
        String token = UserThreadUtils.getToken();
        String userId = UserThreadUtils.getUserId();
        String clientType = UserThreadUtils.getClientType();
        if (StringUtils.isNotBlank(token)) {
            // 删除token对应的用户信息缓存
            CacheUtils.del(RedisKeys.TOKEN.concat(token));
        }
        if (StringUtils.isNotBlank(userId)) {
            // 删除userId与token缓存
            StringCacheUtils.del(RedisKeys.USER_ID_TOKEN.concat(userId).concat("_").concat(clientType));
        }
    }

    @Operation(summary = "创建CSRF Token", description = "一个用户同时只有一个CSRF token有效")
    @PostMapping("/public/getCsrfToken")
    @ApiFreqLimit(time = 600, limit = 100)
    public String getCsrfToken() {
        String csrfToken = IdUtil.simpleUUID();
        String userId = UserThreadUtils.getUserId();
        // 缓存CSRF Token，30分钟有效
        StringCacheUtils.set(RedisKeys.CSRF_TOKEN.concat(userId), csrfToken + "_" + System.currentTimeMillis(), 1800);
        return csrfToken;
    }

    @Operation(summary = "角色扮演", description = "切换当前用户的权限为指定用户的权限")
    @PostMapping("/op/rolePlay")
    @ApiFreqLimit
    public String rolePlay(@RequestBody @Validated RolePlayWrapper params) {
        SysUser user = sysUserService.selectOneById(params.getUserId());
        return getToken(user, params.getClientType(), false);
    }

    @Operation(summary = "通过4a用户信息登录", description = "与akm-4a服务配合，akm-4a服务获取用户信息成功后调用该接口获取token，该接口返回重定向地址")
    @PostMapping("/open/loginByAccount4a")
    @IgnoreResultHandler
    public String loginByAccount4a(@RequestBody String encryptedStr) {
        AkmConfig.Login4a login4a = akmConfig.getLogin4a();
        String userDataJsonStr = EncryptUtils.aesDecrypt(encryptedStr, login4a.getAesKey());
        log.info(userDataJsonStr);
        JSONObject jsonObject = JSONUtil.parseObj(userDataJsonStr);
        String account = jsonObject.getStr("ACCOUNT");
        String msg;
        if (account == null) {
            account = jsonObject.getStr("account");
        }
        if (account == null) {
            msg = "帐号不存在";
            log.error(msg);
            return msg;
        }
        log.info(account);
        SysUser user = sysUserService.selectOneByAccount4a(account);
        if (user == null) {
            // todo
            msg = "4a帐号(" + account + ")未绑定用户";
            log.warn(msg);
            return msg;
        }
        String token = getToken(user, ClientTypeEnum.WEB.getValue(), true);
        return login4a.getSuccessRedirectUrl() + "?token=" + token;
    }

    @Operation(summary = "查询在线用户")
    @PostMapping("/op/onlineUserList")
    @ApiFreqLimit
    public List<CacheUser> onlineUserList() {
        Set<String> keys = CacheUtils.keys(RedisKeys.TOKEN + "*");
        List<CacheUser> userList = new ArrayList<>();
        keys.forEach(key -> {
            CacheUser user = CacheUtils.getNoPrefix(key);
            if (user != null) {
                // 清楚敏感数据
                user.setUserId(null);
                userList.add(user);
            }
        });
        return userList;
    }

    @Operation(summary = "强制用户下线")
    @PostMapping("/op/forcedOffline")
    @ApiFreqLimit
    public void forcedOffline(@RequestBody String token) {
        CacheUser user = CacheUtils.get(RedisKeys.TOKEN.concat(token));
        String userId = UserThreadUtils.getUserId();
        if (userId.equals(user.getUserId())) {
            throw new BusinessException("不能强制下线自己");
        }
        if (user.getRoleList().stream().map(SysRoleBaseInfo::getCode).collect(Collectors.toSet()).contains(ROLE_TENANT_ADMIN_CODE)) {
            throw new BusinessException("不能强制下线管理员账号");
        }
        // 删除token对应的用户信息缓存
        CacheUtils.del(RedisKeys.TOKEN.concat(token));
        // 删除userId与token缓存
        StringCacheUtils.del(RedisKeys.USER_ID_TOKEN.concat(user.getUserId()).concat("_").concat(user.getClientType()));
    }

    /**
     * 用户token超时时长(单位秒)
     * APP 7天零点过期
     * 其他 30分钟后过期
     */
    public long getTokenTimeout(String clientType) {
        if (ClientTypeEnum.APP.getValue().equals(clientType)) {
            DateTime start = DateUtil.date();
            DateTime end = DateUtil.endOfDay(DateUtil.nextWeek());
            return DateUtil.between(start, end, DateUnit.SECOND);
        } else {
            return akmConfig.getTokenTimeout() * 60L;
        }
    }

}
