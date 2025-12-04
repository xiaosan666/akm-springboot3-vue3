package com.akm.springboot3.web.sys.service;

import java.util.List;


public interface SysMenuApiService {

    int updateApiByMenuId(List<String> uriList, String menuId);
}
