package com.akm.springboot3.web.sys.service;

import java.util.List;


public interface SysRoleOrgService {

    int updateOrgDataScopeByRoleId(String roleId, String dataScopeOrg, List<String> orgIdList);

}

