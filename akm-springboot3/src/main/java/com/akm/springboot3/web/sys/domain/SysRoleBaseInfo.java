package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;


@Schema(title = "SysRoleBaseInfo")
@Getter
@Setter
@ToString
public class SysRoleBaseInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;

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
     * 组织机构数据权限范围
     */
    @Schema(title = "组织机构数据权限范围")
    private String dataScopeOrg;

}
