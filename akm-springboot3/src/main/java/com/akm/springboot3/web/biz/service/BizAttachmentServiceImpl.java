package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.file.utils.MinioUtils;
import com.akm.springboot3.web.biz.entity.BizAttachment;
import com.akm.springboot3.web.biz.mapper.BizAttachmentMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class BizAttachmentServiceImpl implements BizAttachmentService {

    @Resource
    private BizAttachmentMapper bizAttachmentMapper;

    @Override
    public int insertOrUpdateSelective(BizAttachment record) {
        if (StringUtils.isBlank(record.getId())) {
            record.setId(SnowflakeUtils.id());
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(new Date());
            List<BizAttachment> list = new ArrayList<>();
            list.add(record);
            return bizAttachmentMapper.batchInsert(list);
        } else {
            return bizAttachmentMapper.updateById(record);
        }
    }

    @Override
    public int batchInsert(List<BizAttachment> list) {
        if (list.isEmpty()) {
            return 0;
        }
        for (BizAttachment item : list) {
            item.setId(SnowflakeUtils.id());
            item.setCreateUserId(UserThreadUtils.getUserId());
            if (item.getCreateTime() == null) {
                item.setCreateTime(new Date());
            }
        }
        return bizAttachmentMapper.batchInsert(list);
    }

    @Override
    public List<BizAttachment> findByAll(BizAttachment bizAttachment) {
        return bizAttachmentMapper.findByAll(bizAttachment);
    }

    @Override
    public List<BizAttachment> findByRecordTypeAndRecordId(String recordType, String recordId) {
        return bizAttachmentMapper.findByRecordTypeAndRecordId(recordType, recordId);
    }

    @Override
    public List<BizAttachment> findByRecordId(String recordId) {
        return bizAttachmentMapper.findByRecordTypeAndRecordId(null, recordId);
    }

    @Override
    public int deleteByRecordTypeAndRecordId(String recordType, String recordId) {
        return bizAttachmentMapper.deleteByRecordTypeAndRecordId(recordType, recordId);
    }

    @Override
    public int deleteByRecordId(String recordId) {
        return bizAttachmentMapper.deleteByRecordTypeAndRecordId(null, recordId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDel(List<String> idList) {
        if (idList.isEmpty()) {
            return 0;
        }
        for (BizAttachment record : bizAttachmentMapper.findByIdList(idList)) {
            try {
                MinioUtils.removeObject(record.getAttachmentUrl());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return bizAttachmentMapper.batchDel(idList);
    }

    @Override
    public List<BizAttachment> findByRecordIdList(List<String> idList) {
        return bizAttachmentMapper.findByRecordIdList(idList);
    }
}


