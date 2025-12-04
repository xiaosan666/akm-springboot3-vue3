package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户表
 */
@Schema(title = "com-akm-springboot-web-sys-entity-SysUser")
@Getter
@Setter
@ToString
public class SysUser implements Serializable {
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;
    /**
     * 租户id
     */
    @Schema(title = "租户id")
    private String tenantId;
    /**
     * 密码
     */
    @Schema(title = "密码")
    private String password;

    /**
     * 密码加盐
     */
    @Schema(title = "密码加盐")
    private String salt;

    /**
     * 用户名
     */
    @Schema(title = "用户名")
    private String username;

    /**
     * 真实姓名
     */
    @Schema(title = "真实姓名")
    private String realname;

    /**
     * 用户头像
     */
    @Schema(title = "用户头像")
    private String avatar;

    /**
     * 是否启用(默认1,禁用0)
     */
    @Schema(title = "是否启用(默认1,禁用0)")
    private Byte enable;

    /**
     * 微信小程序openId
     */
    @Schema(title = "微信小程序openId")
    private String openid;

    /**
     * otp秘钥
     */
    @Schema(title = "otp秘钥")
    private String otpSecret;

    /**
     * 归属组织/部门
     */
    @Schema(title = "归属组织/部门")
    private String orgId;

    /**
     * 账号过期时间
     */
    @Schema(title = "账号过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiredTime;

    /**
     * 密码更新次数
     */
    @Schema(title = "密码更新次数")
    private Byte updatePassword;

    /**
     * 最后一次密码修改时间
     */
    @Schema(title = "最后一次密码修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastPasswordChangeTime;

    /**
     * 创建人用户id
     */
    @Schema(title = "创建人用户id")
    private String createUserId;

    /**
     * 更新人用户id
     */
    @Schema(title = "更新人用户id")
    private String updateUserId;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(title = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 删除标志(默认0,删除1)
     */
    @Schema(title = "删除标志(默认0,删除1)")
    private Integer delFlag;

    /**
     * 租户编码
     */
    @Schema(title = "租户编码")
    private String tenantCode;
}
