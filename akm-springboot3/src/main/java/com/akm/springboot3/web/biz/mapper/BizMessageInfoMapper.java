package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.domain.MessageQuery;
import com.akm.springboot3.web.biz.entity.BizMessageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BizMessageInfoMapper {
    int insertSelective(BizMessageInfo record);

    BizMessageInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BizMessageInfo record);

    List<BizMessageInfo> findByAll(BizMessageInfo bizMessageInfo);

    int batchDel(@Param("idList") List<String> idList);

    int myUnReadMessageCount();

    List<BizMessageInfo> myMessage(MessageQuery query);

}
