package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.domain.AppCheckUpdate;
import com.akm.springboot3.web.biz.entity.BizAppVersion;

import java.util.List;


public interface BizAppVersionService {


    int deleteByPrimaryKey(String id);

    int insertSelective(BizAppVersion record);

    List<BizAppVersion> findByAll(BizAppVersion bizAppVersion);

    BizAppVersion checkUpdate(AppCheckUpdate appCheckUpdate);
}

