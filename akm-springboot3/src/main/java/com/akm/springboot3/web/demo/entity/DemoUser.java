package com.akm.springboot3.web.demo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Schema(title = "用户对象")
@Getter
@Setter
@ColumnWidth(22)
public class DemoUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(title = "编号")
    @ExcelIgnore
    private String id;

    @Schema(title = "真实姓名")
    @ExcelProperty("姓名")
    private String realname;

    @NotBlank(message = "用户名不允许为空")
    @Schema(title = "手机号码")
    @ExcelProperty("手机号码")
    private String username;

    @Schema(title = "年龄")
    @ExcelProperty("年龄")
    @NumberFormat
    private Integer age;

    @Schema(title = "生日", example = "1991/06/17")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @ExcelProperty("生日")

    private Date birthday;

    @Schema(title = "身份证号")
    @ExcelProperty("身份证号")
    private String idCard;

    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("创建时间")
    @ExcelIgnore
    private Date createTime;

    @Schema(title = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("更新时间")
    @ExcelIgnore
    private Date updateTime;

    @Schema(title = "生日过滤开始日期")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @ExcelProperty("生日过滤开始日期")
    @ExcelIgnore
    private Date birthdayBegin;

    @Schema(title = "生日过滤结束日期")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    @ExcelProperty("生日过滤结束日期")
    @ExcelIgnore
    private Date birthdayEnd;

    @Schema(title = "用户名、姓名模糊搜索")
    @ExcelProperty("姓名模糊搜索")
    @ExcelIgnore
    private String searchContent;

    @ExcelIgnore
    private Integer rowNum;

}
