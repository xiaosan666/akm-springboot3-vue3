package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysRoleMenuMapper {
    int batchInsert(@Param("list") List<SysRoleMenu> list);

    int deleteByRoleId(@Param("roleId") String roleId);

    List<String> getMenuIdByUserId(@Param("userId") String userId);
}
