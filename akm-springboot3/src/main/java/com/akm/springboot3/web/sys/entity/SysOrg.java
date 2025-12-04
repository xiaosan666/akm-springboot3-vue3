package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 组织机构表
 */
@Schema(title = "组织机构表")
@Getter
@Setter
public class SysOrg implements Serializable {
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
     * 组织机构全路径(使用左斜杠分割)
     */
    @Schema(title = "组织机构全路径(使用左斜杠分割)")
    private String orgFullName;
    /**
     * 组织机构id全路径(使用英文逗号分割)
     */
    @Schema(title = "组织机构id全路径(使用英文逗号分割)")
    private String orgFullId;
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
     * 是否启用(默认1,禁用0)
     */
    @Schema(title = "是否启用(默认1,禁用0)")
    private Integer enable;
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
    /**
     * 删除标志(默认0,删除1)
     */
    @Schema(title = "删除标志(默认0,删除1)")
    private Integer delFlag;
}
