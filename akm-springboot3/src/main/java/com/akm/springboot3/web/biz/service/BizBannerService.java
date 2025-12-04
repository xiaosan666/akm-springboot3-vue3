package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.entity.BizBanner;

import java.util.List;


public interface BizBannerService {


    BizBanner selectByPrimaryKey(Integer id);

    List<BizBanner> findByType(String type);

}

