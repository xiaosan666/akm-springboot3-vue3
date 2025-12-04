package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.domain.SysDictInfo;
import com.akm.springboot3.web.sys.entity.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysDictMapper {
    int insertSelective(SysDict record);

    int updateByPrimaryKeySelective(SysDict record);

    List<SysDict> findByAll(SysDict sysDict);

    int batchDel(@Param("idList") List<String> idList, @Param("updateUserId") String updateUserId);

    List<SysDictInfo> findByTypes(@Param("typeList") List<String> typeList);
}
