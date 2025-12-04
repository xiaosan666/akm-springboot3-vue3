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
 * 角色和前端资源关系表
 *
 * @author xiaojun
 */
@Schema(title = "com-akm-springboot-web-sys-entity-SysRoleMenu")
@Getter
@Setter
@ToString
public class SysRoleMenu implements Serializable {
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
     * sys_menu表id
     */
    @Schema(title = "sys_menu表id")
    private String menuId;
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
}
