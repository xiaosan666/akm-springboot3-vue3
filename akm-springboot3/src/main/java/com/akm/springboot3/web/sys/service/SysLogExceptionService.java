package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysLogEx;

public interface SysLogExceptionService {


    int insert(SysLogEx record);

    void asyncInsert(SysLogEx record);


}
