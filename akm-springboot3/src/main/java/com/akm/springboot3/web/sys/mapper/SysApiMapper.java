package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysApiMapper {
    int insertSelective(SysApi record);

    int updateByPrimaryKeySelective(SysApi record);

    List<SysApi> findByAll(SysApi sysApi);

    int batchDel(@Param("idList") List<String> idList, @Param("updateUserId") String updateUserId);

    List<String> getUriByRoleId(@Param("roleId") String roleId);
}
