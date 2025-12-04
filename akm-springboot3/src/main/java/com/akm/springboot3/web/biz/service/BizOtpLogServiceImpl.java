package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.*;
import com.akm.springboot3.web.biz.entity.BizOtpLog;
import com.akm.springboot3.web.biz.mapper.BizOtpLogMapper;
import com.akm.springboot3.web.sys.entity.SysUser;
import com.akm.springboot3.web.sys.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BizOtpLogServiceImpl implements BizOtpLogService {

    private final BizOtpLogMapper bizOtpLogMapper;

    private final SysUserService sysUserService;

    BizOtpLogServiceImpl(BizOtpLogMapper bizOtpLogMapper, SysUserService sysUserService) {
        this.bizOtpLogMapper = bizOtpLogMapper;
        this.sysUserService = sysUserService;
    }

    @Override
    public int insert(BizOtpLog record) {
        record.setCreateTime(new Date());
        return bizOtpLogMapper.insert(record);
    }

    @Async("logAkmExecutor")
    @Override
    public void asyncInsert(BizOtpLog record) {
        record.setCreateTime(new Date());
        bizOtpLogMapper.insert(record);
    }

    /**
     * 获取otp二维码数据
     */
    @Override
    public String getQrCodeData() {
        String userId = UserThreadUtils.getUserId();
        SysUser user = sysUserService.selectOneById(userId);
        String otpSecret = user.getOtpSecret();
        if (otpSecret != null) {
            // todo 存在密钥则说明用户已经绑定过，则不在提供二维码
            log.info("用户{}已绑定otp密钥", userId);
            // throw new BusinessException("请打开Google Authenticator应用查看验证码");
        } else {
            otpSecret = GoogleAuthenticator.generateSecretKey();
            sysUserService.updateOtpSecretByUserId(otpSecret, userId);
        }
        return GoogleAuthenticator.getQrCodeData(otpSecret, UserThreadUtils.getUserName());
    }

    /**
     * 检查验证码是否有效，并对访问接口做白名单处理
     */
    @Override
    public void verificationOtpCode(String code, String btnCode) {
        if (code == null) {
            throw new BusinessException("验证码不正确");
        }
        String userId = UserThreadUtils.getUserId();
        SysUser user = sysUserService.selectOneById(userId);
        String otpSecret = user.getOtpSecret();
        if (StringUtils.isBlank(otpSecret)) {
            throw new BusinessException("请使用Google Authenticator应用扫描二维码绑定账号");
        }
        if (!GoogleAuthenticator.checkCode(otpSecret, Long.parseLong(code))) {
            throw new BusinessException("验证码不正确");
        }
        List<String> codeHisList = CacheUtils.get(RedisKeys.OPT_USER_USED_CODE + userId);
        if (codeHisList == null) {
            codeHisList = new ArrayList<>();
        }
        // 判断验证码是否过期
        if (codeHisList.contains(code)) {
            throw new BusinessException("验证码已过期");
        }
        codeHisList.add(code);
        // 缓存已使用过的code
        CacheUtils.set(RedisKeys.OPT_USER_USED_CODE + userId, codeHisList, 3600);
        // 校验通过缓存btnCode
        StringCacheUtils.set(RedisKeys.OPT_ALLOW_BTN_CODE + userId + btnCode, "1", 600);
    }

    /**
     * 判断访问接口是否为白名单
     *
     * @param btnCode       与前端按钮编码一致
     * @param operationName 操作名称
     * @return 是否为白名单
     */
    @Override
    public boolean assertOtpSecret(String btnCode, String operationName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return false;
        }
        HttpServletRequest request = attributes.getRequest();
        String userId = UserThreadUtils.getUserId();
        String cache = StringCacheUtils.get(RedisKeys.OPT_ALLOW_BTN_CODE + userId + btnCode);
        if (cache != null) {
            // 记录日志
            BizOtpLog log = new BizOtpLog();
            log.setId(SnowflakeUtils.id());
            log.setUserId(userId);
            log.setUsername(UserThreadUtils.getUserName());
            log.setRealname(UserThreadUtils.getName());
            log.setOperation(operationName);
            log.setIp(IpUtils.getClientIpAddress(request));
            asyncInsert(log);
            return true;
        }
        return false;
    }

}





