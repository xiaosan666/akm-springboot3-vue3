package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema
@Getter
@Setter
@ToString
public class SysUserUpdatePassword {
    @NotBlank(message = "用户编号不允许为空")
    @Schema(title = "用户编号")
    private String id;

    @NotBlank(message = "旧密码不允许为空")
    @Schema(title = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不允许为空")
    @Schema(title = "新密码")
    private String newPassword;

    /**
     * 后台需要知道密码才能完成是否拼音缩写、复杂度校验
     */
    private String textPass;

}
