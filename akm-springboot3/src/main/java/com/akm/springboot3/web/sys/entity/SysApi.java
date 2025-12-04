package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Schema(title = "com-akm-springboot-web-sys-entity-SysApi")
@Getter
@Setter
@ToString
public class SysApi implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(title = "主键")
    private String id;
    /**
     * 父id，0表示根节点
     */
    @Schema(title = "父id，0表示根节点")
    private String parentId;
    /**
     * 类型：1目录，2uri
     */
    @Schema(title = "类型：1目录，2uri")
    private Byte type;
    /**
     * api接口名称
     */
    @Schema(title = "api接口名称")
    private String name;
    /**
     * service接口的权限验证规则，如：/user/op/save、/user/op/**
     */
    @Schema(title = "service接口的权限验证规则，如：/user/op/save、/user/op/**")
    private String uri;
    /**
     * 备注
     */
    @Schema(title = "备注")
    private String remark;
    /**
     * 排序（倒序）
     */
    @Schema(title = "排序（倒序）")
    private Integer orders;
    /**
     * 创建人用户id
     */
    @Schema(title = "创建人用户id")
    private String createUserId;
    /**
     * 更新人用户id
     */
    @Schema(title = "更新人用户id")
    private String updateUserId;
    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @Schema(title = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 删除标志(默认0,删除1)
     */
    @Schema(title = "删除标志(默认0,删除1)")
    private Integer delFlag;
}
