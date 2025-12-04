package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.domain.SysDictInfo;
import com.akm.springboot3.web.sys.entity.SysDict;

import java.util.List;

public interface SysDictService {

    int insertOrUpdateSelective(SysDict record);

    List<SysDict> findByAll(SysDict record);

    int batchDel(List<String> idList);

    List<SysDictInfo> findByTypes(List<String> typeList);
}











