package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.domain.CacheRoleOrg;
import com.akm.springboot3.web.sys.entity.SysRoleOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleOrgMapper {
    int batchInsert(@Param("list") List<SysRoleOrg> list);

    int deleteByRoleId(@Param("roleId") String roleId);

    List<String> getOrgListByRoleId(@Param("roleId") String roleId);

    List<CacheRoleOrg> findAll();

}
