package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.domain.District;
import com.akm.springboot3.web.biz.entity.BizDistrict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BizDistrictMapper {

    int batchInsert(@Param("list") List<BizDistrict> list);

    int clearTable();

    List<District> findDistrict(District district);

}
