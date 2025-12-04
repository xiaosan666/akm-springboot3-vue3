package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.domain.SysUserDetail;
import com.akm.springboot3.web.sys.domain.SysUserInfo;
import com.akm.springboot3.web.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface SysUserMapper {
    int insertSelective(SysUser record);

    int updateByPrimaryKeySelective(SysUser record);

    int deleteByPrimaryKey(@Param("id") String id, @Param("updateUserId") String updateUserId, @Param("tenantId") String tenantId);

    int updatePassword(@Param("id") String id, @Param("password") String password, @Param("salt") String salt, @Param("updatePassword") int updatePassword, @Param("lastPasswordChangeTime") Date lastPasswordChangeTime, @Param("updateUserId") String updateUserId, @Param("tenantId") String tenantId);

    SysUser selectOneById(@Param("id") String id);

    SysUser selectOneByUsername(@Param("username") String username);

    SysUser selectOneByAccount4a(@Param("account4a") String account4a);

    List<SysUserDetail> findByAll(SysUserDetail detail);

    SysUserInfo selectUserInfoById(@Param("id") String id);

    int updateAvatar(@Param("id") String id, @Param("avatar") String avatar, @Param("tenantId") String tenantId);

    int updateOtpSecretByUserId(@Param("otpSecret") String otpSecret, @Param("userId") String userId, @Param("tenantId") String tenantId);

    int updateExpiredTimeByUserId(@Param("expiredTime") Date expiredTime, @Param("id") String id);
}
