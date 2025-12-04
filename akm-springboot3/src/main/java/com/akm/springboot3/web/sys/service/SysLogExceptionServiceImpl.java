package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysLogEx;
import com.akm.springboot3.web.sys.mapper.SysLogExceptionMapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysLogExceptionServiceImpl implements SysLogExceptionService {

    @Resource
    private SysLogExceptionMapper sysLogExceptionMapper;

    @Override
    public int insert(SysLogEx record) {
        return sysLogExceptionMapper.insert(record);
    }

    @Async("logAkmExecutor")
    @Override
    public void asyncInsert(SysLogEx record) {
        record.setCreateTime(new Date());
        sysLogExceptionMapper.insert(record);
    }

}
