package com.akm.springboot3.web.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Schema(title = "com-akm-springboot-web-biz-entity-BizAppVersion")
@Getter
@Setter
@ToString
public class BizAppVersion implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * ID主键
     */
    @Schema(title = "ID主键")
    private String id;
    /**
     * 记录分类：1Android，2iOS，3小程序，4web
     */
    @Schema(title = "记录分类：1Android，2iOS，3小程序，4web")
    private Byte recordType;
    /**
     * 版本号
     */
    @Schema(title = "版本号")
    private String version;
    /**
     * 更新内容
     */
    @Schema(title = "更新内容")
    private String note;
    /**
     * 是否强制更新（1强制，默认0）
     */
    @Schema(title = "是否强制更新（1强制，默认0）")
    private Byte force;
    /**
     * 更新包下载地址
     */
    @Schema(title = "更新包下载地址")
    private String url;
    /**
     * 是否属于热更新（1是，默认0）
     */
    @Schema(title = "是否属于热更新（1是，默认0）")
    private Byte hot;
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
    /**
     * 删除标志(默认0,删除1)
     */
    @Schema(title = "删除标志(默认0,删除1)")
    private Integer delFlag;
}
