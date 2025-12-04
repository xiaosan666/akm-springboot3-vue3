package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.entity.BizBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BizBannerMapper {
    BizBanner selectByPrimaryKey(Integer id);

    List<BizBanner> findByType(@Param("type") String type);
}
