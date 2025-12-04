package com.akm.springboot3.web.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Schema(title = "SysLog")
@Getter
@Setter
@ColumnWidth(22)
public class SysLog implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(title = "主键")
    @ExcelIgnore
    private String id;
    /**
     * 请求id
     */
    @Schema(title = "请求id")
    @ExcelProperty("请求id")
    private String requestId;
    /**
     * 接口描述/操作描述
     */
    @Schema(title = "接口描述/操作描述")
    @ExcelProperty("接口描述/操作描述")
    private String apiDescription;
    /**
     * URI
     */
    @Schema(title = "URI")
    @ExcelProperty("URI")
    private String uri;
    /**
     * 请求类型
     */
    @Schema(title = "请求类型")
    @ExcelProperty("请求类型")
    private String method;
    /**
     * 请求入参
     */
    @Schema(title = "请求入参")
    @ExcelProperty("请求入参")
    private String parameter;
    /**
     * 响应结果
     */
    @Schema(title = "响应结果")
    @ExcelProperty("响应结果")
    private String result;
    /**
     * 请求是否成功，1成功，0异常；默认1
     */
    @Schema(title = "请求是否成功，1成功，0异常；默认1")
    @ExcelProperty("请求是否成功，1成功，0异常")
    private Integer success;
    /**
     * 操作时间
     */
    @Schema(title = "操作时间")
    @ExcelProperty("操作时间")
    private Long startTime;
    /**
     * 消耗时间
     */
    @Schema(title = "消耗时间")
    @ExcelProperty("消耗时间")
    private Long spendTime;
    /**
     * IP地址
     */
    @Schema(title = "IP地址")
    @ExcelProperty("IP地址")
    private String ip;
    /**
     * URL
     */
    @Schema(title = "URL")
    @ExcelProperty("URL")
    private String url;
    /**
     * 用户标识
     */
    @Schema(title = "用户标识")
    @ExcelProperty("用户标识")
    private String userAgent;
    /**
     * 用户id
     */
    @Schema(title = "用户id")
    @ExcelProperty("用户id")
    private String userId;
    /**
     * 用户名
     */
    @Schema(title = "用户名")
    @ExcelProperty("用户名")
    private String username;
    /**
     * 用户真实姓名
     */
    @Schema(title = "用户真实姓名")
    @ExcelProperty("用户真实姓名")
    private String name;
    /**
     * 应用类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app
     */
    @Schema(title = "应用类型,对应字典表client_type,由于前端分离,后台系统可能要对接多个客户端,如web，app")
    @ExcelProperty("应用类型")
    private String clientType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelIgnore
    private Date updateTime;

    @Schema(title = "操作开始时间")
    @ExcelProperty("操作开始时间")
    @ExcelIgnore
    private Long startTimeBegin;

    @Schema(title = "操作结束时间")
    @ExcelProperty("操作结束时间")
    @ExcelIgnore
    private Long startTimeEnd;
}
