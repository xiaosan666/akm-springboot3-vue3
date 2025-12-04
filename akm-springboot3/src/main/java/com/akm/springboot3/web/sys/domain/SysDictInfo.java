package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据字典表
 */
@Getter
@Setter
@ToString
public class SysDictInfo {
    @Schema(title = "字典类型编码，形如：sex（英文，多个单词下划线分隔）")
    private String type;
    @Schema(title = "名称，形如：男（用于给用户展示）")
    private String label;
    @Schema(title = "值，形如：1（业务表保存该值）")
    private String value;
}
