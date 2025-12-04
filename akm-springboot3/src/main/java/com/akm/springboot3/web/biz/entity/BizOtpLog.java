package com.akm.springboot3.web.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * otp验证码校验成功日志记录表
 */
@Schema(title = "com-akm-springboot-web-biz-entity-BizOtpLog")
@Getter
@Setter
@ToString
public class BizOtpLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(title = "主键id")
    private String id;
    /**
     * 用户id
     */
    @Schema(title = "用户id")
    private String userId;
    /**
     * 用户名
     */
    @Schema(title = "用户名")
    private String username;
    /**
     * 真实姓名
     */
    @Schema(title = "真实姓名")
    private String realname;
    /**
     * 操作描述
     */
    @Schema(title = "操作描述")
    private String operation;
    /**
     * IP地址
     */
    @Schema(title = "IP地址")
    private String ip;
    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
