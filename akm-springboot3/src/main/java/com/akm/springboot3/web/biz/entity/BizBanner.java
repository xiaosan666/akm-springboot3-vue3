package com.akm.springboot3.web.biz.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 轮播图管理表
 *
 * @author xiaojun
 */
@Schema(title = "com-akm-springboot-web-biz-entity-BizBanner")
@Getter
@Setter
@ToString
public class BizBanner implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * ID主键
     */
    @Schema(title = "ID主键")
    private Integer id;
    /**
     * 轮播图分类
     */
    @Schema(title = "轮播图分类")
    private String type;
    /**
     * 名称
     */
    @Schema(title = "名称")
    private String name;
    /**
     * 描述
     */
    @Schema(title = "描述")
    private String description;
    /**
     * 图片地址
     */
    @Schema(title = "图片地址")
    private String imgAddr;
    /**
     * 排序（倒序）
     */
    @Schema(title = "排序（倒序）")
    private Integer orders;
    /**
     * 是否可跳转0：否；1是
     */
    @Schema(title = "是否可跳转0：否；1是")
    private Integer isJump;
    /**
     * 跳转地址
     */
    @Schema(title = "跳转地址")
    private String jumpAddr;
}
