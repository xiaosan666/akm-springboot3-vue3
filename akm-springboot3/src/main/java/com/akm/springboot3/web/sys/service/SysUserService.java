package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.domain.SysUserDetail;
import com.akm.springboot3.web.sys.domain.SysUserInfo;
import com.akm.springboot3.web.sys.domain.SysUserUpdatePassword;
import com.akm.springboot3.web.sys.entity.SysUser;

import java.util.List;

public interface SysUserService {

    String insertSelective(SysUser record);

    String updateByPrimaryKeySelective(SysUser record);

    int updatePassword(SysUserUpdatePassword params, boolean isAdmin);

    int deleteByPrimaryKey(String id);

    SysUser selectOneById(String id);

    SysUser selectOneByUsername(String username);

    SysUser selectOneByAccount4a(String account4a);

    List<SysUserDetail> findByAll(SysUserDetail detail);

    /**
     * 查询用户基本信息
     *
     * @param id 用户id
     * @return 用户基本信息
     */
    SysUserInfo selectUserInfoById(String id);

    /**
     * 更新头像
     *
     * @param id     用户id
     * @param avatar 头像路径
     * @return 更新记录数
     */
    int updateAvatar(String id, String avatar);

    /**
     * 更新otp密钥
     *
     * @param otpSecret 密钥
     * @param userId    用户id
     * @return 更新记录数
     */
    int updateOtpSecretByUserId(String otpSecret, String userId);

    /**
     * 更新账号过期时间
     *
     * @param userId 用户id
     * @return 更新记录数
     */
    int updateExpiredTimeByUserId(String userId);
}














