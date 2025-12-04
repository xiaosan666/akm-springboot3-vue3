package com.akm.springboot3.web.demo.mapper;

import com.akm.springboot3.web.demo.entity.DemoUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DemoUserMapper {
    int insertSelective(DemoUser record);

    int updateByPrimaryKeySelective(DemoUser record);

    int deleteByPrimaryKey(String id);

    DemoUser selectByPrimaryKey(String id);

    List<DemoUser> findByAll(DemoUser demoUser);

    String findUsernameById(String id);

    int getTotal();

    int batchInsert(@Param("list") List<DemoUser> list);

    List<String> findByUserNameList(@Param("list") List<String> list);
}
