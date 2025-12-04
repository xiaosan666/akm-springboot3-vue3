package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.entity.BizAttachment;

import java.util.List;


public interface BizAttachmentService {

    int insertOrUpdateSelective(BizAttachment record);

    int batchInsert(List<BizAttachment> list);

    List<BizAttachment> findByAll(BizAttachment bizAttachment);

    List<BizAttachment> findByRecordTypeAndRecordId(String recordType, String recordId);

    List<BizAttachment> findByRecordId(String recordId);

    int deleteByRecordTypeAndRecordId(String recordType, String recordId);

    int deleteByRecordId(String recordId);

    int batchDel(List<String> idList);

    List<BizAttachment> findByRecordIdList(List<String> idList);

}


