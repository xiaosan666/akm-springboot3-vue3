package com.akm.springboot3.web.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 前端资源表
 */
@Schema(title = "com-akm-springboot-web-sys-entity-SysMenu")
@Getter
@Setter
@ToString
public class SysMenu implements Serializable {
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
     * 资源类型1:目录,2:菜单,3:按钮,4:其他
     */
    @Schema(title = "资源类型1:目录,2:菜单,3:按钮,4:其他")
    private Byte type;
    /**
     * 资源名称
     */
    @Schema(title = "资源名称")
    private String name;
    /**
     * 菜单uri
     */
    @Schema(title = "菜单uri")
    private String uri;
    /**
     * 资源编码，前端根据该code控制资源的显示隐藏
     */
    @Schema(title = "资源编码，前端根据该code控制资源的显示隐藏")
    private String code;
    /**
     * 资源图标class
     */
    @Schema(title = "资源图标class")
    private String icon;
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
     * 应用类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app
     */
    @Schema(title = "应用类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app")
    private Byte clientType;
    /**
     * 是否启用(默认1,禁用0)
     */
    @Schema(title = "是否启用(默认1,禁用0)")
    private Byte enable;
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
    /**
     * 动态创建路由（1是，0否）
     */
    @Schema(title = "动态创建路由（1是，0否）")
    private Integer dynamicRoute;

    private List<String> uriList;
}
