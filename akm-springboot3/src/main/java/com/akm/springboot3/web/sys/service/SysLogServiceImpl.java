package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysLog;
import com.akm.springboot3.web.sys.entity.SysLogEx;
import com.akm.springboot3.web.sys.entity.SysLogLogin;
import com.akm.springboot3.web.sys.mapper.SysLogMapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int insert(SysLog record) {
        record.setCreateTime(new Date());
        return sysLogMapper.insert(record);
    }


    @Async("logAkmExecutor")
    @Override
    public void asyncInsert(SysLog record) {
        record.setCreateTime(new Date());
        sysLogMapper.insert(record);
    }

    @Override
    public List<SysLog> findByAll(SysLog sysLog) {
        return sysLogMapper.findByAll(sysLog);
    }

    @Override
    public List<SysLogLogin> findByAllLoginLog(SysLog sysLog) {
        return sysLogMapper.findByAllLoginLog(sysLog);
    }

    @Override
    public List<SysLogEx> findByAllExceptionLog(SysLog sysLog) {
        return sysLogMapper.findByAllExceptionLog(sysLog);
    }

    @Override
    public SysLog getOneById(String id) {
        return sysLogMapper.getOneById(id);
    }

    @Override
    public SysLogEx getExceptionLogById(String id) {
        return sysLogMapper.getExceptionLogById(id);
    }

}








