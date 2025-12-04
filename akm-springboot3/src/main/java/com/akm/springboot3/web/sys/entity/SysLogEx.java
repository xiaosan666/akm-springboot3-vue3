package com.akm.springboot3.web.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Schema(title = "com.akm.springboot3.web.sys.entity.SysLogException")
@Getter
@Setter
@ColumnWidth(22)
public class SysLogEx extends SysLog {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 对应sys_log表id
     */
    @Schema(title = "对应sys_log表id")
    @ExcelIgnore
    private String logId;
    /**
     * 1.BusinessException；9.其他Exception
     */
    @Schema(title = "1.BusinessException；9.其他Exception")
    @ExcelProperty("异常类型：1.BusinessException；9.其他Exception")
    private Byte type;
    /**
     * 异常堆栈信息
     */
    @Schema(title = "异常堆栈信息")
    @ExcelProperty("异常堆栈信息")
    private String content;
}
