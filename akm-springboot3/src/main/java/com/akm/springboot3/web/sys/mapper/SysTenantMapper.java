package com.akm.springboot3.web.sys.mapper;

import com.akm.springboot3.web.sys.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaojun
 */
@Mapper
public interface SysTenantMapper {
    int insertSelective(SysTenant record);

    int updateByPrimaryKeySelective(SysTenant record);

    int deleteByPrimaryKey(@Param("id") String id, @Param("updateUserId") String updateUserId);

    SysTenant selectByPrimaryKey(String id);

    List<SysTenant> findByAll(SysTenant sysTenant);

}
