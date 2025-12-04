package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.entity.BizMessageUserRead;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BizMessageUserReadMapper {
    int insertSelective(BizMessageUserRead record);

    BizMessageUserRead selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BizMessageUserRead record);

    BizMessageUserRead selectOneByMessageIdAndUserId(@Param("messageId") String messageId, @Param("userId") String userId);


}
