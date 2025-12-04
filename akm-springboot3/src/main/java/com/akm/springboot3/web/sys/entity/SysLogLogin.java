package com.akm.springboot3.web.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Schema(title = "SysLogLogin")
@Getter
@Setter
@ColumnWidth(22)
public class SysLogLogin extends SysLog {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 对应sys_log表id
     */
    @Schema(title = "对应sys_log表id")
    @ExcelIgnore
    private String logId;
}
