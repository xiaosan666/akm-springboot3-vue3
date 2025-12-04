package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.entity.BizMessageOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BizMessageOrgMapper {

    int batchInsert(@Param("list") List<BizMessageOrg> list);

    int deleteByMessageId(@Param("messageId") String messageId);

    List<BizMessageOrg> selectByMessageId(@Param("messageId") String messageId);

}
