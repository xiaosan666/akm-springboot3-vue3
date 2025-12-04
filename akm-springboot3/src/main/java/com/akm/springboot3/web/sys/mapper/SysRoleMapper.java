package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysRoleMapper {
    int insertSelective(SysRole record);

    int updateByPrimaryKeySelective(SysRole record);

    List<SysRole> findByAll(SysRole sysRole);

    int batchDel(@Param("idList") List<String> idList, @Param("updateUserId") String updateUserId, @Param("tenantId") String tenantId);

    List<SysRoleBaseInfo> findRoleByUser(@Param("userId") String userId);

    int updateDataScorpOrgById(@Param("dataScopeOrg") String dataScopeOrg, @Param("id") String id);

}
