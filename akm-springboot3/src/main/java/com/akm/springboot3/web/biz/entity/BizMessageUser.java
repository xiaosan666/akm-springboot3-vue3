package com.akm.springboot3.web.biz.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息下发人员表
 */
@Schema(title = "消息下发人员表")
@Getter
@Setter
public class BizMessageUser implements Serializable {
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;

    /**
     * 消息表id
     */
    @Schema(title = "消息表id")
    private String messageId;

    /**
     * 用户id
     */
    @Schema(title = "用户id")
    private String userId;

    /**
     * 用户姓名
     */
    @Schema(title = "用户姓名")
    private String userName;

    /**
     * 创建人用户id
     */
    @Schema(title = "创建人用户id")
    private String createUserId;

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private Date createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}
