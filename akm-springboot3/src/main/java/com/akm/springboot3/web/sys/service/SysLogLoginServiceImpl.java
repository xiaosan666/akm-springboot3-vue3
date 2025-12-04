package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysLogLogin;
import com.akm.springboot3.web.sys.mapper.SysLogLoginMapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysLogLoginServiceImpl implements SysLogLoginService {

    @Resource
    private SysLogLoginMapper sysLogLoginMapper;

    @Override
    public int insert(SysLogLogin record) {
        return sysLogLoginMapper.insert(record);
    }

    @Async("logAkmExecutor")
    @Override
    public void asyncInsert(SysLogLogin record) {
        record.setCreateTime(new Date());
        sysLogLoginMapper.insert(record);
    }

}
