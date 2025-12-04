package com.akm.springboot3.web.biz.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息下发单位表
 */
@Schema(title = "消息下发单位表")
@Getter
@Setter
public class BizMessageOrg implements Serializable {
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
     * 单位/部门id
     */
    @Schema(title = "单位/部门id")
    private String orgId;

    /**
     * 单位/部门名称
     */
    @Schema(title = "单位/部门名称")
    private String orgName;

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
