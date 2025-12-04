package com.akm.springboot3.web.demo.api;

import com.akm.springboot3.core.annotation.CheckCsrfToken;
import com.akm.springboot3.core.domain.PageQuery;
import com.akm.springboot3.core.domain.PageResult;
import com.akm.springboot3.file.utils.FileUtils;
import com.akm.springboot3.file.utils.MinioUtils;
import com.akm.springboot3.web.demo.entity.DemoUser;
import com.akm.springboot3.web.demo.service.DemoUserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.github.pagehelper.PageHelper;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FilterInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Tag(name = "demo-用户管理")
@RestController
@RequestMapping("/auth/demo/user/open")
@Slf4j
public class DemoUserApi {

    private final DemoUserService demoUserService;

    DemoUserApi(DemoUserService service) {
        this.demoUserService = service;
    }

    @Operation(summary = "新增/修改", description = "id为空表示新增")
    @PostMapping("/op/insertOrUpdate")
    @CheckCsrfToken
    public int insertOrUpdate(@RequestBody @Validated DemoUser demoUser) {
        return demoUserService.insertOrUpdateSelective(demoUser);
    }

    @Operation(summary = "列表查询（分页）")
    @PostMapping("/view/findPage")
    public PageResult<DemoUser> findPage(@RequestBody PageQuery<DemoUser> pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getOrderBy());
        List<DemoUser> list = demoUserService.findByAll(pageQuery.getCondition());
        return new PageResult<>(list);
    }

    @Operation(summary = "根据编号删除用户")
    @PostMapping("/op/del")
    public int del(@RequestBody String id) {
        return demoUserService.deleteByPrimaryKey(id);
    }

    @Operation(summary = "列表查询")
    @PostMapping("/view/findList")
    public List<DemoUser> findList(@RequestBody DemoUser demoUser) {
        return demoUserService.findByAll(demoUser);
    }

    @Operation(summary = "根据id查询用户名")
    @PostMapping("/view/findUsernameById")
    public String findUsernameById(@RequestBody String id) {
        return demoUserService.findUsernameById(id);
    }

    @Operation(summary = "获取总数")
    @PostMapping("/view/getTotal")
    public int getTotal() {
        return demoUserService.getTotal();
    }

    @Operation(summary = "导入")
    @PostMapping("/op/import")
    public void doRead(@Schema(title = "文件服务器存储key") @RequestBody String objectKey) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        FilterInputStream in = MinioUtils.getObject(FileUtils.formatKey(objectKey));
        EasyExcelFactory.read(in, DemoUser.class, new DemoDataListener(demoUserService)).sheet().headRowNumber(2).doRead();
    }

    @Operation(summary = "同步导入", description = "数据量大时不推荐使用")
    @PostMapping("/op/importSync")
    public long doReadSync(@Schema(title = "文件服务器存储key") @RequestBody String objectKey) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        FilterInputStream in = MinioUtils.getObject(FileUtils.formatKey(objectKey));
        List<DemoUser> list = EasyExcelFactory.read(in).head(DemoUser.class).sheet().headRowNumber(2).doReadSync();
        log.info("读取到数据数量:{}", list.size());
        demoUserService.batchInsert(list);
        return list.size();
    }

    @Operation(summary = "导出")
    @PostMapping("/op/export")
    public void export(@RequestBody DemoUser demoUser, HttpServletResponse response) throws IOException {
        List<DemoUser> users = demoUserService.findByAll(demoUser);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment");
        EasyExcel.write(response.getOutputStream(), DemoUser.class).sheet("Sheet1").doWrite(users);
    }

}
