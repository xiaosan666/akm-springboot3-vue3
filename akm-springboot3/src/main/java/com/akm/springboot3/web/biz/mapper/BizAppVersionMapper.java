package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.domain.AppCheckUpdate;
import com.akm.springboot3.web.biz.entity.BizAppVersion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BizAppVersionMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(BizAppVersion record);

    List<BizAppVersion> findByAll(BizAppVersion bizAppVersion);

    List<BizAppVersion> findNewVersionRecord(AppCheckUpdate appCheckUpdate);
}
