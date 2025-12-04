package com.akm.springboot3.web.sys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Schema(title = "SysUserDetail")
@Getter
@Setter
@ToString
public class SysUserDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
     * 租户名称
     */
    @Schema(title = "租户名称")
    private String tenantName;

    /**
     * /**
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
     * 最近一次密码修改时间
     */
    @Schema(title = "最近一次密码修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastPasswordChangeTime;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 模糊搜索查询条件
     */
    @Schema(title = "根据用户名、姓名、手机号码模糊查询")
    private String searchContent;

    /**
     * 归属组织名称
     */
    private String orgName;

    /**
     * 用户角色名称（多个逗号分隔）
     */
    private String roleNames;

    /**
     * 用户姓名（多个逗号分隔）用于批量查询
     */
    private String realnames;
}
