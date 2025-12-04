package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.domain.District;

import java.util.List;


public interface BizDistrictService {
    void init();

    List<District> findDistrict(District district);
}
