package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录信息
 */
@Getter
@Setter
public class LoginWrapper {
    @NotNull(message = "客户端类型不允许为空")
    @Schema(title = "客户端类型类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app", required = true)
    private String clientType;

    @NotBlank(message = "用户名不允许为空")
    @Schema(title = "用户名", required = true)
    private String username;

    @NotBlank(message = "密码不允许为空")
    @Schema(title = "密码", required = true)
    private String password;

    @Schema(title = "验证码")
    private String code;

    @Schema(title = "验证码对应缓存的key")
    private String codeKey;

}
