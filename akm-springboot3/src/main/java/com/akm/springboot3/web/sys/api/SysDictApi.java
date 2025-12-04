package com.akm.springboot3.web.sys.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.annotation.IgnoreSysLog;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;
import com.akm.springboot3.core.utils.AssertUtils;
import com.akm.springboot3.web.sys.domain.SysDictInfo;
import com.akm.springboot3.web.sys.entity.SysDict;
import com.akm.springboot3.web.sys.service.SysDictService;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Tag(name = "sys-数据字典管理")
@RestController
@RequestMapping("/auth/sys/dict")
public class SysDictApi {

    private final SysDictService sysDictService;

    SysDictApi(SysDictService service) {
        this.sysDictService = service;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody SysDict record) {
        return sysDictService.insertOrUpdateSelective(record);
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findPage")
    public PageResult<SysDict> findPage(@RequestBody PageQuery<SysDict> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<SysDict> list = sysDictService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "根据id数组批量删除")
    @PostMapping("/op/batchDel")
    public int batchDel(@RequestBody List<String> idList) {
        AssertUtils.notEmpty(idList, "删除的编号不能为空");
        return sysDictService.batchDel(idList);
    }

    @IgnoreSysLog
    @Operation(summary = "根据字典类型数组查询字典数据列表")
    @PostMapping("/public/findByTypes")
    public List<SysDictInfo> findByTypes(@Schema(title = "字典类型数组") @RequestBody List<String> typeList) {
        return sysDictService.findByTypes(typeList);
    }

    @Operation(summary = "根据字典类型查询(无需登录)")
    @PostMapping("/open/findByType")
    public List<SysDictInfo> findByTypeNoLogin(@Schema(title = "字典类型") @RequestBody String type) {
        String[] allowTypes = {"api_code_msg"};
        if (!Arrays.asList(allowTypes).contains(type)) {
            throw new BusinessException(CodeMsg.FORBIDDEN);
        }
        return sysDictService.findByTypes(Collections.singletonList(type));
    }

}
