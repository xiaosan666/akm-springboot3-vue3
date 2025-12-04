package com.akm.springboot3.web.biz.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(title = "消息通知信息表")
@Getter
@Setter
public class MessageQuery {
    @Schema(title = "主键")
    private String id;

    @Schema(title = "模糊搜索")
    private String searchContent;

    @Schema(title = "消息分类(1消息/站内信；5公告；9其他)")
    private Integer messageType;

    @Schema(title = "消息优先级(1普通；5紧急)")
    private Integer messagePriority;

    @Schema(title = "是否已读（0未读；1已读）")
    private Integer isRead;

    private String orgId;

    private String userId;
}
