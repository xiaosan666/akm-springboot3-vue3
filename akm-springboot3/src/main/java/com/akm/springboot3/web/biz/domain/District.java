package com.akm.springboot3.web.biz.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class District {
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

}
