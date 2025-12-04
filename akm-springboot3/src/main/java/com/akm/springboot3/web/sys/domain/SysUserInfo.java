package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Schema(title = "SysUserInfo")
@Getter
@Setter
@ToString
public class SysUserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @Schema(title = "租户id")
    private String tenantId;

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
     * 用户角色列表
     */
    private List<SysRoleInfo> roleList;

}
