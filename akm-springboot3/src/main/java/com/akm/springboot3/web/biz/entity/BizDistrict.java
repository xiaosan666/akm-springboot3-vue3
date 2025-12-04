package com.akm.springboot3.web.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 行政区域信息表
 *
 * @author xiaojun
 */
@Schema(title = "com-akm-springboot-web-biz-entity-BizDistrict")
@Getter
@Setter
public class BizDistrict implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 区域编码
     */
    @Schema(title = "区域编码")
    private String adCode;
    /**
     * 父级区域编码
     */
    @Schema(title = "父级区域编码")
    private String parentAdCode;
    /**
     * 行政区名称
     */
    @Schema(title = "行政区名称")
    private String name;
    /**
     * 区域中心点经纬度
     */
    @Schema(title = "区域中心点经纬度")
    private String center;
    /**
     * 行政区划级别（province:省份，city:市，district:区县）
     */
    @Schema(title = "行政区划级别（province:省份，city:市，district:区县）")
    private String level;
    /**
     * 创建人用户id
     */
    @Schema(title = "创建人用户id")
    private String createUserId;
    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
