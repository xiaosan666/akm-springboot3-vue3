package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录账号密码历史记录表
 */
@Schema(title = "用户登录账号密码历史记录表")
@Getter
@Setter
public class SysUserPassHistory implements Serializable {
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;

    /**
     * 用户id
     */
    @Schema(title = "用户id")
    private String userId;

    /**
     * 密码
     */
    @Schema(title = "密码")
    private String password;

    /**
     * 密码加盐
     */
    @Schema(title = "密码加盐")
    private String salt;

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

    @Serial
    private static final long serialVersionUID = 1L;
}
