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
 * 附件表（业务与文件关系表）
 *
 * @author xiaojun
 */
@Schema(title = "com-akm-springboot-web-biz-entity-BizAttachment")
@Getter
@Setter
@ToString
public class BizAttachment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(title = "主键id")
    private String id;
    /**
     * 业务模块分类标志
     */
    @Schema(title = "业务模块分类标志")
    private String recordType;
    /**
     * 业务表主键id
     */
    @Schema(title = "业务表主键id")
    private String recordId;
    /**
     * 附件名称（原文件名称）
     */
    @Schema(title = "附件名称（原文件名称）")
    private String attachmentName;
    /**
     * 附件路径
     */
    @Schema(title = "附件路径")
    private String attachmentUrl;
    /**
     * 附件大小，单位Byte
     */
    @Schema(title = "附件大小，单位Byte")
    private String attachmentSize;
    /**
     * 创建人用户id
     */
    @Schema(title = "创建人用户id")
    private String createUserId;
    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
