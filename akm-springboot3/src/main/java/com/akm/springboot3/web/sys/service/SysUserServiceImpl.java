package com.akm.springboot3.web.sys.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.datascope.DataScopeOrg;
import com.akm.springboot3.core.datascope.DataScopeService;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.*;
import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.domain.SysUserDetail;
import com.akm.springboot3.web.sys.domain.SysUserInfo;
import com.akm.springboot3.web.sys.domain.SysUserUpdatePassword;
import com.akm.springboot3.web.sys.entity.SysUser;
import com.akm.springboot3.web.sys.entity.SysUserPassHistory;
import com.akm.springboot3.web.sys.mapper.SysUserMapper;
import com.akm.springboot3.web.sys.mapper.SysUserPassHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.akm.springboot3.core.utils.UserThreadUtils.ROLE_TENANT_ADMIN_CODE;

@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final DataScopeService dataScopeService;

    private final AkmConfig akmConfig;

    private final SysRoleService sysRoleService;

    private final SysUserPassHistoryMapper sysUserPassHistoryMapper;

    SysUserServiceImpl(SysUserMapper sysUserMapper, DataScopeService dataScopeService, AkmConfig akmConfig, SysRoleService sysRoleService, SysUserPassHistoryMapper sysUserPassHistoryMapper) {
        this.sysUserMapper = sysUserMapper;
        this.dataScopeService = dataScopeService;
        this.akmConfig = akmConfig;
        this.sysRoleService = sysRoleService;
        this.sysUserPassHistoryMapper = sysUserPassHistoryMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePassword(SysUserUpdatePassword params, boolean isAdmin) {
        String userId = params.getId();
        // 断言数据权限
        dataScopeService.assertUserId(userId);
        SysUser user = selectOneById(userId);
        AssertUtils.notNull(user, "用户不存在");
        // 记录密码更新次数，管理员修改密码重置为0
        int countUpdate = 0;
        if (!isAdmin) {
            String p = EncryptUtils.getPassword(params.getOldPassword(), user.getSalt(), user.getId());
            AssertUtils.isTrue(p.equals(user.getPassword()), "旧密码错误");
            countUpdate = user.getUpdatePassword() + 1;
        }

        String textPass = EncryptUtils.base64Decode(params.getTextPass());
        AssertUtils.isTrue(PasswordComplexityValidateUtils.validateSensitive(textPass), "密码不能包含敏感字符");
        AssertUtils.isTrue(PasswordComplexityValidateUtils.validateContainsNamePinyin(textPass, user.getRealname()), "密码不能包含姓名拼音");

        // 新密码不能与上次密码一样
        List<SysUserPassHistory> passHistoryList = sysUserPassHistoryMapper.findByUserId(userId);
        if (passHistoryList != null) {
            for (SysUserPassHistory lastPassHistory : passHistoryList) {
                String lastPass = EncryptUtils.getPassword(EncryptUtils.sha256(textPass), lastPassHistory.getSalt(), user.getId());
                AssertUtils.isTrue(!lastPass.equals(lastPassHistory.getPassword()), "新密码不能与历史密码最近5次相同");
            }
        }

        String salt = EncryptUtils.getSalt();
        String password = EncryptUtils.getPassword(params.getNewPassword(), salt, userId);

        // 记录密码修改历史
        SysUserPassHistory passHistory = new SysUserPassHistory();
        passHistory.setId(SnowflakeUtils.id());
        passHistory.setUserId(userId);
        passHistory.setPassword(password);
        passHistory.setSalt(salt);
        passHistory.setCreateTime(new Date());
        passHistory.setCreateUserId(UserThreadUtils.getUserId());
        sysUserPassHistoryMapper.insert(passHistory);


        String tenantId = UserThreadUtils.isPlatformAdmin() ? null : UserThreadUtils.getTenantId();
        return sysUserMapper.updatePassword(userId, password, salt, countUpdate, null, UserThreadUtils.getUserId(), tenantId);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        String userId = UserThreadUtils.getUserId();
        if (userId.equals(id)) {
            throw new BusinessException("不能删除自己");
        }
        // 断言数据权限
        dataScopeService.assertUserId(id);
        String tenantId = UserThreadUtils.isPlatformAdmin() ? null : UserThreadUtils.getTenantId();
        List<SysRoleBaseInfo> roles = sysRoleService.findRoleByUser(id);
        AssertUtils.isTrue(!roles.stream().map(SysRoleBaseInfo::getCode).collect(Collectors.toSet()).contains(ROLE_TENANT_ADMIN_CODE), "不能删除管理员账号");
        return sysUserMapper.deleteByPrimaryKey(id, userId, tenantId);
    }

    @Override
    public SysUser selectOneById(String id) {
        // 断言数据权限
        dataScopeService.assertUserId(id);
        return sysUserMapper.selectOneById(id);
    }

    @Override
    public SysUser selectOneByUsername(String username) {
        // 断言数据权限
        dataScopeService.assertUserName(username);
        return sysUserMapper.selectOneByUsername(username);
    }

    @Override
    public SysUser selectOneByAccount4a(String account4a) {
        return sysUserMapper.selectOneByAccount4a(account4a);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertSelective(SysUser record) {
        String username = record.getUsername();
        AssertUtils.notBlank(username, "用户名不能为空");
        String password = record.getPassword();
        AssertUtils.notBlank(record.getPassword(), "密码不能为空");
        // 新增操作使用sysUserMapper进行查询不经过数据权限判断
        AssertUtils.isTrue(sysUserMapper.selectOneByUsername(username) == null, "用户名或手机号码已经存在");
        String userId = SnowflakeUtils.id();
        record.setId(userId);
        // 若不是平台管理员角色，强制设置租户为当前用户所属租户
        if (!UserThreadUtils.isPlatformAdmin()) {
            record.setTenantId(UserThreadUtils.getTenantId());
        }
        String salt = EncryptUtils.getSalt();
        record.setSalt(salt);
        record.setPassword(EncryptUtils.getPassword(password, salt, userId));
        record.setLastPasswordChangeTime(new Date());
        record.setCreateUserId(UserThreadUtils.getUserId());
        record.setCreateTime(new Date());
        sysUserMapper.insertSelective(record);
        return userId;
    }

    @Override
    public String updateByPrimaryKeySelective(SysUser record) {
        String userId = record.getId();
        AssertUtils.notBlank(userId, "用户编号不能为空");
        // 若不是平台管理员角色，强制设置租户为当前用户所属租户
        if (!UserThreadUtils.isPlatformAdmin()) {
            record.setTenantId(UserThreadUtils.getTenantId());
        }

        if (record.getEnable() == 0) {
            List<SysRoleBaseInfo> roles = sysRoleService.findRoleByUser(userId);
            AssertUtils.isTrue(!roles.stream().map(SysRoleBaseInfo::getCode).collect(Collectors.toSet()).contains(ROLE_TENANT_ADMIN_CODE), "不能禁用超级管理员账号");
        }

        record.setUpdateUserId(UserThreadUtils.getUserId());
        record.setUpdateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(record);

        // userId与token关系key；注：以下concat顺序不能改
        String userIdTokenKey = RedisKeys.USER_ID_TOKEN.concat(userId);
        // todo 修改用户信息，让用户重新登陆
        Set<String> tokenKeys = StringCacheUtils.keys(userIdTokenKey);
        for (String key : tokenKeys) {
            String oldToken = StringCacheUtils.getNoPrefix(key);
            if (oldToken != null) {
                CacheUtils.del(RedisKeys.TOKEN.concat(oldToken));
            }
        }
        return userId;
    }

    @Override
    @DataScopeOrg(orgFiledAlias = "t1.org_id", userFileAlias = "t1.id")
    public List<SysUserDetail> findByAll(SysUserDetail detail) {
        // 若不是平台管理员角色，强制设置租户为当前用户所属租户
        if (!UserThreadUtils.isPlatformAdmin()) {
            detail.setTenantId(UserThreadUtils.getTenantId());
        }
        // 模糊搜索查询条件
        String searchContent = detail.getSearchContent();
        if (StringUtils.isNotBlank(searchContent)) {
            // 若包含中文逗号，则替换为英文
            searchContent = searchContent.replace("，", ",");
            // 若包含英文逗号，则认为是按多个姓名查询，如张三,李四
            if (searchContent.contains(",")) {
                detail.setRealnames(searchContent);
                detail.setSearchContent(null);
            }
        }
        return sysUserMapper.findByAll(detail);
    }

    @Override
    public SysUserInfo selectUserInfoById(String id) {
        // 断言数据权限
        dataScopeService.assertUserId(id);
        return sysUserMapper.selectUserInfoById(id);
    }

    @Override
    public int updateAvatar(String id, String avatar) {
        // 断言数据权限
        dataScopeService.assertUserId(id);
        String tenantId = UserThreadUtils.isPlatformAdmin() ? null : UserThreadUtils.getTenantId();
        return sysUserMapper.updateAvatar(id, avatar, tenantId);
    }

    @Override
    public int updateOtpSecretByUserId(String otpSecret, String userId) {
        String tenantId = UserThreadUtils.isPlatformAdmin() ? null : UserThreadUtils.getTenantId();
        return sysUserMapper.updateOtpSecretByUserId(otpSecret, userId, tenantId);
    }

    @Override
    public int updateExpiredTimeByUserId(String userId) {
        // 断言数据权限
        dataScopeService.assertUserId(userId);
        Integer accountEffectiveDays = akmConfig.getAccountEffectiveDays();
        DateTime expiredTime = accountEffectiveDays != -1 ? DateUtil.offsetDay(new Date(), accountEffectiveDays) : null;
        return sysUserMapper.updateExpiredTimeByUserId(expiredTime, userId);
    }

}














