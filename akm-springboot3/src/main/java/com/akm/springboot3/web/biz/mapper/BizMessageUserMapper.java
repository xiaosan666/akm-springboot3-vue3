package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.entity.BizMessageUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BizMessageUserMapper {
    int batchInsert(@Param("list") List<BizMessageUser> list);

    int deleteByMessageId(@Param("messageId") String messageId);

    List<BizMessageUser> selectByMessageId(@Param("messageId") String messageId);


}
