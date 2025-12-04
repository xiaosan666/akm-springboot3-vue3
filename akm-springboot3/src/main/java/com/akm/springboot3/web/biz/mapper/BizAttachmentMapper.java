package com.akm.springboot3.web.biz.mapper;

import com.akm.springboot3.web.biz.entity.BizAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BizAttachmentMapper {

    int batchInsert(@Param("list") List<BizAttachment> list);

    List<BizAttachment> findByAll(BizAttachment bizAttachment);

    List<BizAttachment> findByRecordTypeAndRecordId(@Param("recordType") String recordType, @Param("recordId") String recordId);

    int deleteByRecordTypeAndRecordId(@Param("recordType") String recordType, @Param("recordId") String recordId);

    int batchDel(@Param("idList") List<String> idList);

    int updateById(BizAttachment bizAttachment);

    List<BizAttachment> findByIdList(@Param("idList") List<String> idList);

    List<BizAttachment> findByRecordIdList(@Param("idList") List<String> idList);

}
