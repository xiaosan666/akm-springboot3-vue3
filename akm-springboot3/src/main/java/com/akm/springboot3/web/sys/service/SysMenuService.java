package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.domain.SysMenuInfo;
import com.akm.springboot3.web.sys.entity.SysMenu;

import java.util.List;


public interface SysMenuService {

    int insertOrUpdateSelective(SysMenu record);

    List<SysMenu> findByAll(SysMenu record);

    int batchDel(List<String> idList);

    List<SysMenuInfo> findMenu();

}


