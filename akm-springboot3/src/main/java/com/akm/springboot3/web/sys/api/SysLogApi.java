package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.IgnoreSysLog;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.web.sys.domain.RunLogReq;
import com.akm.springboot3.web.sys.entity.SysLog;
import com.akm.springboot3.web.sys.entity.SysLogEx;
import com.akm.springboot3.web.sys.entity.SysLogLogin;
import com.akm.springboot3.web.sys.service.SysLogService;
import com.alibaba.excel.EasyExcelFactory;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.SpreadsheetVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;


@Tag(name = "sys-系统日志管理")
@RestController
@RequestMapping("/auth/sys/log")
@Slf4j
public class SysLogApi {

    @Value("${logging.file.path}")
    private String logPath;

    private final SysLogService sysLogService;

    SysLogApi(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @IgnoreSysLog
    @Operation(summary = "登录日志列表（分页）")
    @PostMapping("/view/login/findPage")
    public PageResult<SysLogLogin> findPageLoginLog(@RequestBody PageQuery<SysLog> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<SysLogLogin> list = sysLogService.findByAllLoginLog(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @IgnoreSysLog
    @Operation(summary = "异常日志列表（分页）")
    @PostMapping("/view/exception/findPage")
    public PageResult<SysLogEx> findPageExceptionLog(@RequestBody PageQuery<SysLog> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<SysLogEx> list = sysLogService.findByAllExceptionLog(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @IgnoreSysLog
    @Operation(summary = "请求日志列表（分页）")
    @PostMapping("/view/request/findPage")
    public PageResult<SysLog> findPageRequestLog(@RequestBody PageQuery<SysLog> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<SysLog> list = sysLogService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @IgnoreSysLog
    @Operation(summary = "登录日志详情")
    @PostMapping("/view/login/detail")
    public SysLog getLoginLog(@RequestBody String id) {
        return sysLogService.getOneById(id);
    }

    @IgnoreSysLog
    @Operation(summary = "异常日志详情")
    @PostMapping("/view/exception/detail")
    public SysLogEx getExceptionLog(@RequestBody String id) {
        return sysLogService.getExceptionLogById(id);
    }

    @IgnoreSysLog
    @Operation(summary = "请求日志详情")
    @PostMapping("/view/request/detail")
    public SysLog getRequestLog(@RequestBody String id) {
        return sysLogService.getOneById(id);
    }

    @Operation(summary = "登录日志列表导出")
    @PostMapping("/op/login/export")
    public void loginExport(@RequestBody SysLog sysLog, HttpServletResponse response) throws IOException {
        List<SysLogLogin> list = sysLogService.findByAllLoginLog(sysLog);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment");
        EasyExcelFactory.write(response.getOutputStream(), SysLogLogin.class).sheet("Sheet1").doWrite(list);
    }

    @Operation(summary = "异常日志列表导出")
    @PostMapping("/op/exception/export")
    public void exceptionExport(@RequestBody SysLog sysLog, HttpServletResponse response) throws IOException {
        List<SysLogEx> list = sysLogService.findByAllExceptionLog(sysLog);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment");
        EasyExcelFactory.write(response.getOutputStream(), SysLogEx.class).sheet("Sheet1").doWrite(list);
    }

    @Operation(summary = "请求日志列表导出")
    @PostMapping("/op/request/export")
    public void requestExport(@RequestBody SysLog sysLog, HttpServletResponse response) throws IOException {
        List<SysLog> list = sysLogService.findByAll(sysLog);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment");
        resetCellMaxTextLength();
        EasyExcelFactory.write(response.getOutputStream(), SysLog.class).sheet("Sheet1").doWrite(list);
    }

    @PostMapping("/view/run_log")
    public LinkedList<String> runLog(@RequestBody RunLogReq runLogReq) throws IOException {
        log.info("\n\n------- 刷新日志 -------\n\n");
        // 日志文件路径，如./logs/all_log.log
        String logFile = logPath + "/" + runLogReq.getLogFileName();
        runLogReq.setMaxLine(runLogReq.getMaxLine() > 5000 ? 5000 : runLogReq.getMaxLine());
        runLogReq.setMaxLine(runLogReq.getMaxLine() < 100 ? 100 : runLogReq.getMaxLine());
        java.io.File file = new java.io.File(logFile);
        if (!file.exists()) {
            throw new BusinessException("日志文件不存在: " + logFile);
        }
        // 先定位到文件末尾向前1MB（或文件全部）
        long fileLength = file.length();
        int bufferSize = 1024 * 1024; // 1MB
        long start = Math.max(0, fileLength - bufferSize);
        java.util.LinkedList<String> lines = new java.util.LinkedList<>();
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(file, "r")) {
            raf.seek(start);
            // 跳过第一个不完整的行
            if (start != 0) raf.readLine();
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(raf.getFD()), java.nio.charset.StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                    if (lines.size() > runLogReq.getMaxLine()) {
                        lines.removeFirst();
                    }
                }
            }
        }
        return lines;
    }
    /**
     * 处理EasyExcel导出时提示“单元格存储内容超过了最大 32767 个字符”的解决方法
     */
    private static void resetCellMaxTextLength() {
        SpreadsheetVersion excel2007 = SpreadsheetVersion.EXCEL2007;
        if (Integer.MAX_VALUE != excel2007.getMaxTextLength()) {
            Field field;
            try {
                field = excel2007.getClass().getDeclaredField("_maxTextLength");
                field.setAccessible(true);
                field.set(excel2007, Integer.MAX_VALUE);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
