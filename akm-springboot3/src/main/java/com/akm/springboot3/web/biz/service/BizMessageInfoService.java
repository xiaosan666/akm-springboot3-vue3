package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.core.utils.SnowflakeUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.biz.domain.MessageQuery;
import com.akm.springboot3.web.biz.entity.*;
import com.akm.springboot3.web.biz.mapper.BizMessageInfoMapper;
import com.akm.springboot3.web.biz.mapper.BizMessageOrgMapper;
import com.akm.springboot3.web.biz.mapper.BizMessageUserMapper;
import com.akm.springboot3.web.biz.mapper.BizMessageUserReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BizMessageInfoService {

    private final BizMessageInfoMapper bizMessageInfoMapper;
    private final BizMessageOrgMapper bizMessageOrgMapper;
    private final BizMessageUserMapper bizMessageUserMapper;
    private final BizMessageUserReadMapper bizMessageUserReadMapper;
    private final BizAttachmentService bizAttachmentService;

    BizMessageInfoService(BizMessageInfoMapper bizMessageInfoMapper,
                          BizMessageOrgMapper bizMessageOrgMapper,
                          BizMessageUserMapper bizMessageUserMapper,
                          BizMessageUserReadMapper bizMessageUserReadMapper,
                          BizAttachmentService bizAttachmentService) {
        this.bizMessageInfoMapper = bizMessageInfoMapper;
        this.bizMessageOrgMapper = bizMessageOrgMapper;
        this.bizMessageUserMapper = bizMessageUserMapper;
        this.bizMessageUserReadMapper = bizMessageUserReadMapper;
        this.bizAttachmentService = bizAttachmentService;
    }

    private static final String MESSAGE_ATTACHMENT_RECORD_TYPE = "message";

    @Transactional(rollbackFor = Exception.class)
    public int insertOrUpdateSelective(BizMessageInfo record) {
        Date currentTime = new Date();
        String messageId = record.getId();
        if (StringUtils.isNotBlank(messageId)) {
            // 先清空消息绑定的组织或用户
            bizMessageOrgMapper.deleteByMessageId(messageId);
            bizMessageUserMapper.deleteByMessageId(messageId);
            record.setUpdateUserId(UserThreadUtils.getUserId());
            record.setUpdateTime(currentTime);
            bizMessageInfoMapper.updateByPrimaryKeySelective(record);
        } else {
            messageId = SnowflakeUtils.id();
            record.setId(messageId);
            record.setCreateUserId(UserThreadUtils.getUserId());
            record.setCreateTime(currentTime);
            bizMessageInfoMapper.insertSelective(record);
        }

        // rangeType 发送范围类型(1全部用户；2指定单位/部门；3指定用户)
        // messageStatus 消息状态(0暂存；1下发)
        // 判断有没有选择具体下发范围
        if (record.getRangeType() == 2 && record.getMessageStatus() == 1) {
            List<BizMessageOrg> orgList = record.getOrgList();
            AssertUtils.isTrue(orgList != null && !orgList.isEmpty(), "请选择需要下发的单位");
        }
        // 判断有没有选择具体下发范围
        if (record.getRangeType() == 3 && record.getMessageStatus() == 1) {
            List<BizMessageOrg> orgList = record.getOrgList();
            List<BizMessageUser> userList = record.getUserList();
            boolean hasOrg = orgList != null && !orgList.isEmpty();
            boolean hasUser = userList != null && !userList.isEmpty();
            if (!hasOrg && !hasUser) {
                throw new RuntimeException("请选择需要下发的单位或人员");
            }
        }
        // 绑定消息与组织关系
        List<BizMessageOrg> orgList = record.getOrgList();
        if (orgList != null && !orgList.isEmpty()) {
            for (BizMessageOrg dto : orgList) {
                dto.setId(SnowflakeUtils.id());
                dto.setMessageId(messageId);
                dto.setCreateTime(currentTime);
                dto.setCreateUserId(UserThreadUtils.getUserId());
            }
            bizMessageOrgMapper.batchInsert(orgList);
        }
        // 绑定消息与用户关系
        List<BizMessageUser> userList = record.getUserList();
        if (userList != null && !userList.isEmpty()) {
            for (BizMessageUser dto : userList) {
                dto.setId(SnowflakeUtils.id());
                dto.setMessageId(messageId);
                dto.setCreateTime(currentTime);
                dto.setCreateUserId(UserThreadUtils.getUserId());
            }
            bizMessageUserMapper.batchInsert(userList);
        }

        // 删除原有附件，并插入新的附件
        bizAttachmentService.deleteByRecordTypeAndRecordId(MESSAGE_ATTACHMENT_RECORD_TYPE, record.getId());
        if (record.getBizAttachments() != null && !record.getBizAttachments().isEmpty()) {
            for (BizAttachment bizAttachment : record.getBizAttachments()) {
                bizAttachment.setRecordId(messageId);
                bizAttachment.setRecordType(MESSAGE_ATTACHMENT_RECORD_TYPE);
            }
            bizAttachmentService.batchInsert(record.getBizAttachments());
        }
        return 1;
    }

    public List<BizMessageInfo> findByAll(BizMessageInfo record) {
        record.setDelFlag(0);
        return bizMessageInfoMapper.findByAll(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public int batchDel(List<String> idList) {
        if (idList.isEmpty()) {
            return 0;
        }
        for (String id : idList) {
            bizAttachmentService.deleteByRecordTypeAndRecordId(MESSAGE_ATTACHMENT_RECORD_TYPE, id);
        }
        return bizMessageInfoMapper.batchDel(idList);
    }

    public BizMessageInfo getDetail(String messageId) {
        BizMessageInfo info = bizMessageInfoMapper.selectByPrimaryKey(messageId);
        if (info != null) {
            info.setOrgList(bizMessageOrgMapper.selectByMessageId(messageId));
            info.setUserList(bizMessageUserMapper.selectByMessageId(messageId));
            info.setBizAttachments(bizAttachmentService.findByRecordTypeAndRecordId(MESSAGE_ATTACHMENT_RECORD_TYPE, messageId));
        }
        return info;
    }

    public int myUnReadMessageCount() {
        return bizMessageInfoMapper.myUnReadMessageCount();
    }

    public List<BizMessageInfo> myMessage(MessageQuery query) {
        String userId = UserThreadUtils.getUserId();
        String orgId = UserThreadUtils.getOrgId();
        query.setUserId(userId);
        query.setOrgId(orgId);
        // 查询我的所有消息
        List<BizMessageInfo> messageInfoList = bizMessageInfoMapper.myMessage(query);
        if (messageInfoList.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> messageIdList = messageInfoList.stream().map(BizMessageInfo::getId).collect(Collectors.toList());
        // 查询所有附件
        List<BizAttachment> attachmentList = bizAttachmentService.findByRecordIdList(messageIdList);
        // 消息绑定附件
        for (BizMessageInfo messageInfo : messageInfoList) {
            messageInfo.setBizAttachments(attachmentList.stream().filter(attachment -> messageInfo.getId().equals(attachment.getRecordId())).collect(Collectors.toList()));
        }
        return messageInfoList;
    }

    public void isRead(String messageId) {
        String userId = UserThreadUtils.getUserId();
        // 判断是否已经阅读过
        BizMessageUserRead isReadRecord = bizMessageUserReadMapper.selectOneByMessageIdAndUserId(messageId, userId);
        // 没有阅读记录，则新增一条
        if (isReadRecord == null) {
            BizMessageUserRead entity = new BizMessageUserRead();
            entity.setId(SnowflakeUtils.id());
            entity.setMessageId(messageId);
            entity.setUserId(userId);
            entity.setCreateTime(new Date());
            entity.setCreateUserId(UserThreadUtils.getUserId());
            bizMessageUserReadMapper.insertSelective(entity);
        }
    }

}
