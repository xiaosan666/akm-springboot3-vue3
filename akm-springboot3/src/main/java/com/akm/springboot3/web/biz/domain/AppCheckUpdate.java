package com.akm.springboot3.web.biz.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AppCheckUpdate {

    @NotNull(message = "查询类型不允许为空")
    @Schema(title = "1:Android，2:iOS", required = true)
    private Integer recordType;

    @NotBlank(message = "APPID不允许为空")
    @Schema(title = "APPID", required = true)
    private String appId;

    @NotBlank(message = "当前版本不允许为空")
    @Schema(title = "当前版本", required = true)
    private String version;
}
