package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    int batchInsert(@Param("list") List<SysUserRole> list);

    int deleteByUserId(@Param("userId") String userId);

    List<String> findByRoleId(@Param("roleId") String roleId);

}
