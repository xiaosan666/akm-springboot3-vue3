package com.akm.springboot3.web.sys.service;

import com.akm.springboot3.web.sys.entity.SysTenant;

import java.util.List;

/**
 * @author xiaojun
 */
public interface SysTenantService {


    int deleteByPrimaryKey(String id);

    SysTenant selectByPrimaryKey(String id);

    List<SysTenant> findByAll(SysTenant sysTenant);

    int insertOrUpdateSelective(SysTenant record);

}
