package com.akm.springboot3.web.demo.api;


import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.config.AkmConfig;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.SpringContextHolder;
import com.akm.springboot3.core.utils.StringCacheUtils;
import com.akm.springboot3.web.sys.domain.SysUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@Tag(name = "demo-缓存")
@RestController
@RequestMapping("/demo/cache/open")
@Slf4j
public class DemoCacheApi {

    @Operation(summary = "获取当前缓存类型(local/redis)")
    @PostMapping("/cacheType")
    public String cacheType() {
        AkmConfig kitProps = SpringContextHolder.getBean(AkmConfig.class);
        return "当前缓存类型：" + kitProps.getCacheType();
    }

    @Operation(summary = "String数据类型的缓存/setString")
    @PostMapping("/setString")
    public String setString() {
        StringCacheUtils.set("string", "测试");
        return "保存成功,请访问/getString查询";
    }

    @Operation(summary = "String数据类型的缓存/getString")
    @PostMapping("/getString")
    public String getString() {
        String string = StringCacheUtils.get("string");
        log.info(string);
        return string;
    }

    @Operation(summary = "SysUserDetail数据类型的缓存/setObj")
    @PostMapping("/setObj")
    public String setObj() {
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setId("1");
        sysUserDetail.setRealname("张三");
        sysUserDetail.setExpiredTime(new Date());
        CacheUtils.set("obj", sysUserDetail);
        log.info(JSONUtil.toJsonStr(sysUserDetail));
        return "保存成功,请访问/getObj查询";
    }

    @Operation(summary = "SysUserDetail数据类型的缓存/getObj")
    @PostMapping("/getObj")
    public SysUserDetail getObj() {
        Object obj = CacheUtils.get("obj");
        SysUserDetail sysUserDetail = (SysUserDetail) obj;
        log.info(JSONUtil.toJsonStr(sysUserDetail));
        return sysUserDetail;
    }

    @Operation(summary = "testObj")
    @PostMapping("/testObj")
    public SysUserDetail testObj() {
        SysUserDetail sysUserDetail = new SysUserDetail();
        sysUserDetail.setId("1");
        sysUserDetail.setRealname("张三");
        sysUserDetail.setExpiredTime(new Date());
        CacheUtils.set("obj1", sysUserDetail);
        return CacheUtils.get("obj1");
    }

    @Operation(summary = "testMap")
    @PostMapping("/testMap")
    public Map<String, String> testMap() {
        Map<String, String> map = new HashMap<>(4);
        map.put("a", "a");
        map.put("b", "b");
        CacheUtils.set("map1", map);
        return CacheUtils.get("map1");
    }

    @Operation(summary = "testDict")
    @PostMapping("/testDict")
    public Dict testDict() {
        Dict dict = Dict.create().set("privateKey1", "privateKey1")
            .set("publicKey1", "publicKey1");
        CacheUtils.set("dict1", dict);
        return CacheUtils.get("dict1");
    }

    @Operation(summary = "List<SysUserDetail>数据类型的缓存/setList")
    @PostMapping("/setList")
    public String setList() {
        List<SysUserDetail> list = new ArrayList<>();
        int len = 10;
        for (int i = 0; i < len; i++) {
            SysUserDetail sysUserDetail = new SysUserDetail();
            sysUserDetail.setId(String.valueOf(i));
            sysUserDetail.setRealname("张三");
            sysUserDetail.setExpiredTime(new Date());
            list.add(sysUserDetail);
        }
        CacheUtils.setnx("list", list);
        return "保存成功,请访问/getList查询";
    }

    @Operation(summary = "List<SysUserDetail>数据类型的缓存/getList")
    @PostMapping("/getList")
    @SuppressWarnings("unchecked")
    public List<SysUserDetail> getList() {
        Object obj = CacheUtils.get("list");
        List<SysUserDetail> list = null;
        if (obj != null) {
            list = (List<SysUserDetail>) obj;

        }
        return list;
    }

    @Operation(summary = "使用StringCacheUtils删除String类型的缓存")
    @PostMapping("/delByStringCacheUtils")
    public String delByStringCacheUtils(@RequestBody String key) {
        StringCacheUtils.del(key);
        return "操作完成";
    }

    @Operation(summary = "使用CacheUtils删除String类型的缓存")
    @PostMapping("/delByCacheUtils")
    public String delByCacheUtils(@RequestBody String key) {
        CacheUtils.del(key);
        return "操作完成";
    }

}
