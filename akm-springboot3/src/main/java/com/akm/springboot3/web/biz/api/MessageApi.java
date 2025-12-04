package com.akm.springboot3.web.biz.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.web.biz.domain.MessageQuery;
import com.akm.springboot3.web.biz.entity.BizMessageInfo;
import com.akm.springboot3.web.biz.service.BizMessageInfoService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "biz-消息通知")
@RestController
@RequestMapping("/share/public/biz/message")
public class MessageApi {

    private final BizMessageInfoService messageInfoService;

    MessageApi(BizMessageInfoService messageInfoService) {
        this.messageInfoService = messageInfoService;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody BizMessageInfo record) {
        return messageInfoService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findPage")
    public PageResult<BizMessageInfo> findPage(@RequestBody PageQuery<BizMessageInfo> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<BizMessageInfo> list = messageInfoService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return messageInfoService.batchDel(idList);
    }

    @Operation(summary = "根据id查询详情")
    @PostMapping("/view/detail")
    public BizMessageInfo detail(@RequestBody String messageId) {
        return messageInfoService.getDetail(messageId);
    }

    @Operation(summary = "我的消息-未读消息数量")
    @PostMapping("/my/unReadMessageCount")
    public int myUnReadMessageCount() {
        return messageInfoService.myUnReadMessageCount();
    }

    @Operation(summary = "我的消息-分页查询")
    @PostMapping("/my/message")
    public PageResult<BizMessageInfo> myMessage(@RequestBody PageQuery<MessageQuery> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<BizMessageInfo> list = messageInfoService.myMessage(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "我的消息-记录已读状态")
    @PostMapping("/my/read")
    public void isRead(@RequestBody String messageId) {
        messageInfoService.isRead(messageId);
    }

}
