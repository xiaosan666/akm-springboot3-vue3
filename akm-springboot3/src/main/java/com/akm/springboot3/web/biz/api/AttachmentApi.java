package com.akm.springboot3.web.biz.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.core.utils.StringUtils;
import com.akm.springboot3.web.biz.entity.BizAttachment;
import com.akm.springboot3.web.biz.service.BizAttachmentService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Tag(name = "biz-附件管理")
@RestController
@RequestMapping("/share/public/biz/attachment")
@Validated
public class AttachmentApi {

    private final BizAttachmentService bizAttachmentService;

    AttachmentApi(BizAttachmentService service) {
        this.bizAttachmentService = service;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody BizAttachment record) {
        return bizAttachmentService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "批量新增附件")
    @PostMapping("/op/batchInsert")
    public int batchInsert(@RequestBody @Valid List<BizAttachment> list) {
        return bizAttachmentService.batchInsert(list);
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findPage")
    public PageResult<BizAttachment> findPage(@RequestBody PageQuery<BizAttachment> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<BizAttachment> list = bizAttachmentService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findAll")
    public List<BizAttachment> findAll(@RequestBody BizAttachment record) {
        if (StringUtils.isBlank(record.getRecordId()) && StringUtils.isBlank(record.getRecordType())) {
            throw new BusinessException("请传入业务类型和业务ID不能均为空");
        }
        return bizAttachmentService.findByAll(record);
    }

    @Operation(summary = "删除业务相关附件")
    @PostMapping("/op/deleteAttachment")
    public int deleteAttachment(@RequestBody Map<String, String> map) {
        String recordType = map.get("recordType");
        String recordId = map.get("recordId");
        return bizAttachmentService.deleteByRecordTypeAndRecordId(recordType, recordId);
    }


    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return bizAttachmentService.batchDel(idList);
    }

}
