package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.domain.SysMenuInfo;
import com.akm.springboot3.web.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysMenuMapper {
    int insertSelective(SysMenu record);

    int updateByPrimaryKeySelective(SysMenu record);

    List<SysMenu> findByAll(SysMenu record);

    int batchDel(@Param("idList") List<String> idList, @Param("updateUserId") String updateUserId);

    List<SysMenuInfo> findMenu(@Param("clientType") Integer clientType, @Param("userId") String userId);
}
