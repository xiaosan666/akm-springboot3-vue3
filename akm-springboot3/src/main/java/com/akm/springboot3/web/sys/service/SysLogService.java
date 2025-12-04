package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysLog;
import com.akm.springboot3.web.sys.entity.SysLogEx;
import com.akm.springboot3.web.sys.entity.SysLogLogin;

import java.util.List;

public interface SysLogService {

    int insert(SysLog record);

    void asyncInsert(SysLog record);

    List<SysLog> findByAll(SysLog sysLog);

    List<SysLogLogin> findByAllLoginLog(SysLog sysLog);

    List<SysLogEx> findByAllExceptionLog(SysLog sysLog);

    /**
     * 根据请求日志id获取请求日志详情
     *
     * @param id 请求日志id
     * @return 请求日志详情
     */
    SysLog getOneById(String id);

    /**
     * 根据请求日志id获取异常日志详情
     *
     * @param id 请求日志id
     * @return 异常日志详情
     */
    SysLogEx getExceptionLogById(String id);

}








