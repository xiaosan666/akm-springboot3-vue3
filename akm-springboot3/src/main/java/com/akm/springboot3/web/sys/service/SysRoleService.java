package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.domain.SysRoleBaseInfo;
import com.akm.springboot3.web.sys.entity.SysRole;

import java.util.List;

public interface SysRoleService {

    int insertOrUpdateSelective(SysRole record);

    int batchDel(List<String> idList);

    List<SysRole> findAll(SysRole record);

    List<SysRoleBaseInfo> findRoleByUser(String userId);

}




