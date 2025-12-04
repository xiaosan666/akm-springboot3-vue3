package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysLogEx;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogExceptionMapper {
    int insert(SysLogEx record);
}
