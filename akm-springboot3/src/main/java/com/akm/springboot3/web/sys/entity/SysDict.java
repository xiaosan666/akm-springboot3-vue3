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
 * 数据字典表
 */
@Schema(title = "com-akm-springboot-web-sys-entity-SysDict")
@Getter
@Setter
@ToString
public class SysDict implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;
    /**
     * 字典类型编码，形如：sex（英文，多个单词下划线分隔）
     */
    @Schema(title = "字典类型编码，形如：sex（英文，多个单词下划线分隔）")
    private String type;
    /**
     * 名称，形如：男（用于给用户展示）
     */
    @Schema(title = "名称，形如：男（用于给用户展示）")
    private String label;
    /**
     * 值，形如：1（业务表保存该值）
     */
    @Schema(title = "值，形如：1（业务表保存该值）")
    private String value;
    /**
     * 字典码
     */
    @Schema(title = "字典码")
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
}
