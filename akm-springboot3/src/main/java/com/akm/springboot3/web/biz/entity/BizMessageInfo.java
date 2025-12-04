package com.akm.springboot3.web.biz.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 消息通知信息表
 */
@Schema(title = "消息通知信息表")
@Getter
@Setter
public class BizMessageInfo implements Serializable {
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;

    /**
     * 标题
     */
    @Schema(title = "标题")
    private String title;

    /**
     * 内容
     */
    @Schema(title = "内容")
    private String content;

    /**
     * 消息分类
     */
    @Schema(title = "消息分类(1消息/站内信；5公告；9其他)")
    private Integer messageType;

    /**
     * 消息优先级(1普通；5紧急)
     */
    @Schema(title = "消息优先级(1普通；5紧急)")
    private Integer messagePriority;

    /**
     * 消息状态(0暂存；1下发)
     */
    @Schema(title = "消息状态(0暂存；1下发)")
    private Integer messageStatus;

    /**
     * 发送范围类型(1全部用户；2指定单位/部门；3指定用户)
     */
    @Schema(title = "发送范围类型(1全部用户；2指定单位/部门；3指定用户)")
    private Integer rangeType;

    /**
     * 发送范围明细
     */
    @Schema(title = "发送范围明细")
    private Integer rangeStr;

    /**
     * 业务场景分类(冗余字段，分区不同业务下发的消息)
     */
    @Schema(title = "业务场景分类(冗余字段，分区不同业务下发的消息)")
    private String bizRecordType;

    /**
     * 业务场景业务id
     */
    @Schema(title = "业务场景业务id")
    private String bizRecordId;

    /**
     * 业务跳转链接地址
     */
    @Schema(title = "业务跳转链接地址")
    private String bizUrl;

    /**
     * 业务根据需要存储其他内容
     */
    @Schema(title = "业务根据需要存储其他内容")
    private String bizRemark;

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

    /**
     * 更新人用户id
     */
    @Schema(title = "更新人用户id")
    private String updateUserId;

    /**
     * 更新时间
     */
    @Schema(title = "更新时间")
    private Date updateTime;

    /**
     * 删除标志(默认0,删除1)
     */
    @Schema(title = "删除标志(默认0,删除1)")
    private Integer delFlag;

    /**
     * 删除时间
     */
    @Schema(title = "删除时间")
    private Date delTime;

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "消息下发的单位")
    private List<BizMessageOrg> orgList;

    @Schema(title = "消息下发的用户")
    private List<BizMessageUser> userList;

    @Schema(title = "是否已读（1已读）")
    private Integer isRead;

    @Schema(title = "消息附件")
    private List<BizAttachment> bizAttachments;
}
