package com.akm.springboot3.web.demo.service;

import com.akm.springboot3.web.demo.entity.DemoUser;

import java.util.List;

public interface DemoUserService {

    int deleteByPrimaryKey(String id);

    DemoUser selectByPrimaryKey(String id);

    int insertOrUpdateSelective(DemoUser record);

    List<DemoUser> findByAll(DemoUser demoUser);

    String findUsernameById(String id);

    int getTotal();

    int batchInsert(List<DemoUser> list);
}


