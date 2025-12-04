package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysLogLogin;

public interface SysLogLoginService {


    int insert(SysLogLogin record);

    void asyncInsert(SysLogLogin record);

}
