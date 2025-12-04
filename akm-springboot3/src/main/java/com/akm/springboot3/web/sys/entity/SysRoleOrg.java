package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色部门关系表
 */
@Schema(title = "角色部门关系表")
@Getter
@Setter
public class SysRoleOrg implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;
    /**
     * sys_role表id
     */
    @Schema(title = "sys_role表id")
    private String roleId;
    /**
     * sys_log表id
     */
    @Schema(title = "sys_log表id")
    private String orgId;
    /**
     * 创建人用户id
     */
    @Schema(title = "创建人用户id")
    private String createUserId;
    /**
     * 创建时间（自动更新）
     */
    @Schema(title = "创建时间（自动更新）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新人用户id
     */
    @Schema(title = "更新人用户id")
    private String updateUserId;
    /**
     * 更新时间（自动更新）
     */
    @Schema(title = "更新时间（自动更新）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
