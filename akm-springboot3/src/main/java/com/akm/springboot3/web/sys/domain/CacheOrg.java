package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织机构表
 */
@Getter
@Setter
public class CacheOrg {
    @Schema(title = "ID主键")
    private String id;

    @Schema(title = "父级组织ID")
    private String parentId;

    @Schema(title = "组织编码")
    private String orgCode;

    @Schema(title = "组织名称")
    private String name;

    @Schema(title = "组织机构等级")
    private String level;

    @Schema(title = "组织机构等级中文名称")
    private String levelName;

    @Schema(title = "组织机构全路径(使用左斜杠分割)")
    private String orgFullName;

    @Schema(title = "组织机构id全路径(使用英文逗号分割)")
    private String orgFullId;

    @Schema(title = "排序（倒序）")
    private Integer orders;

    @Schema(title = "是否启用(默认1,禁用0)")
    private Integer enable;
}
