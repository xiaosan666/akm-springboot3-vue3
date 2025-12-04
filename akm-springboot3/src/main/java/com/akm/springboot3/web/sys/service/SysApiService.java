package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysApi;

import java.util.List;

public interface SysApiService {

    int insertOrUpdateSelective(SysApi record);

    List<SysApi> findByAll(SysApi record);

    int batchDel(List<String> idList);

    List<String> getUriByRoleId(String roleId);

    List<String> getAndCacheUriByRoleId(String roleId);

}

















