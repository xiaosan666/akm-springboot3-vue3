package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysMenuApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysMenuApiMapper {
    int batchInsert(@Param("list") List<SysMenuApi> list);

    int deleteByMenuId(String menuId);
}
