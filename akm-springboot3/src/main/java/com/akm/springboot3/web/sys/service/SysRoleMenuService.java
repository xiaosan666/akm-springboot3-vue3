package com.akm.springboot3.web.sys.service;

import java.util.List;


public interface SysRoleMenuService {

    int updateMenuByRoleId(List<String> menuIdList, String roleId);

    /**
     * 查询用户的菜单id列表
     *
     * @param userId 用户ID
     * @return 菜单ID列表
     */
    List<String> getMenuIdByUserId(String userId);
}

