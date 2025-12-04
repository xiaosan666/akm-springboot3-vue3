package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysUserPassHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserPassHistoryMapper {
    int insert(SysUserPassHistory record);

    List<SysUserPassHistory> findByUserId(@Param("userId") String userId);
}
