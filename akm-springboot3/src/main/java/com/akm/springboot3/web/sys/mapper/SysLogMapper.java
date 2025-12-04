package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysLog;
import com.akm.springboot3.web.sys.entity.SysLogEx;
import com.akm.springboot3.web.sys.entity.SysLogLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogMapper {
    int insert(SysLog record);

    List<SysLog> findByAll(SysLog sysLog);

    List<SysLogLogin> findByAllLoginLog(SysLog sysLog);

    List<SysLogEx> findByAllExceptionLog(SysLog sysLog);

    SysLog getOneById(@Param("id") String id);

    SysLogEx getExceptionLogById(@Param("id") String id);

}
