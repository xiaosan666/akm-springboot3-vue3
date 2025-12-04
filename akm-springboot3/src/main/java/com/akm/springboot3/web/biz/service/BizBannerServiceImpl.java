package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.entity.BizBanner;
import com.akm.springboot3.web.biz.mapper.BizBannerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BizBannerServiceImpl implements BizBannerService {

    @Resource
    private BizBannerMapper bizBannerMapper;

    @Override
    public BizBanner selectByPrimaryKey(Integer id) {
        return bizBannerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BizBanner> findByType(String type) {
        return bizBannerMapper.findByType(type);
    }

}

