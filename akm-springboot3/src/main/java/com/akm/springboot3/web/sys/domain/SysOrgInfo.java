package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class SysOrgInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * ID主键
     */
    @Schema(title = "ID主键")
    private String id;
    /**
     * 父级组织ID
     */
    @Schema(title = "父级组织ID")
    private String parentId;
    /**
     * 组织编码
     */
    @Schema(title = "组织编码")
    private String orgCode;
    /**
     * 组织名称
     */
    @Schema(title = "组织名称")
    private String name;
    /**
     * 组织机构等级
     */
    @Schema(title = "组织机构等级")
    private String level;
    /**
     * 组织机构等级中文名称
     */
    @Schema(title = "组织机构等级中文名称")
    private String levelName;
    /**
     * 组织机构全路径(使用英文逗号分割)
     */
    @Schema(title = "组织机构全路径(使用英文逗号分割)")
    private String orgFullName;
    /**
     * 组织机构id全路径(使用英文逗号分割)
     */
    @Schema(title = "组织机构id全路径(使用英文逗号分割)")
    private String orgFullId;
}
