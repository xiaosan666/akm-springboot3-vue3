package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色版本请求参数
 *
 * @author xiaojun
 */
@Getter
@Setter
public class RolePlayWrapper {
    @NotNull(message = "客户端类型不允许为空")
    @Schema(title = "客户端类型类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app", required = true)
    private String clientType;

    @NotBlank(message = "用户编号不允许为空")
    @Schema(title = "用户id", required = true)
    private String userId;
}
