package com.akm.springboot3.web.sys.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysMenuInfo {
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

}
