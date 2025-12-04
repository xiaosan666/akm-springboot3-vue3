package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysLogLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysLogLoginMapper {
    int insert(SysLogLogin record);
}
