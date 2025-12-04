package com.akm.springboot3.web.sys.service;

import java.util.List;

public interface SysUserRoleService {

    int updateRoleByUserId(List<String> roleIdList, String userId);

    List<String> findByRoleId(String roleId);

}
