package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 角色表
 */
@Schema(title = "com-akm-springboot-web-sys-entity-SysRole")
@Getter
@Setter
@ToString
public class SysRole implements Serializable {
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
     * 角色名称
     */
    @Schema(title = "角色名称")
    private String name;
    /**
     * 角色编码
     */
    @Schema(title = "角色编码")
    private String code;
    /**
     * 备注
     */
    @Schema(title = "备注")
    private String remark;
    /**
     * 排序（倒序）
     */
    @Schema(title = "排序（倒序）")
    private Integer orders;
    /**
     * 组织机构数据权限范围，字典类型：data_scope_org
     */
    @Schema(title = "组织机构数据权限范围，字典类型：data_scope_org")
    private String dataScopeOrg;
    /**
     * 是否启用(默认1,禁用0)
     */
    @Schema(title = "是否启用(默认1,禁用0)")
    private Byte enable;
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

    @Schema(title = "组织机构数据权限范围")
    private String dataScopeOrgName;

    @Schema(title = "组织机构ids")
    private String orgIds;

    @Schema(title = "菜单ids")
    private String menuIds;
}
