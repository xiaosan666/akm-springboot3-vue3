package com.akm.springboot3.web.biz.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.UserThreadUtils;
import com.akm.springboot3.web.biz.domain.District;
import com.akm.springboot3.web.biz.entity.BizDistrict;
import com.akm.springboot3.web.biz.mapper.BizDistrictMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class BizDistrictServiceImpl implements BizDistrictService {

    @Resource
    private BizDistrictMapper bizDistrictMapper;

    private static List<BizDistrict> getDistrictData(List<?> districts, String parentAdCode) {
        List<BizDistrict> data = new ArrayList<>();
        String userId = UserThreadUtils.getUserId();
        Date date = new Date();
        for (Object o : districts) {
            JSONObject json = (JSONObject) o;
            String level = json.getStr("level");
            String name = json.getStr("name");
            // 不需要街道数据和台湾省数据
            if ("street".equals(level) || "台湾省".equals(name)) {
                continue;
            }
            BizDistrict district = new BizDistrict();
            String adCode = json.getStr("adcode");
            district.setAdCode(adCode);
            district.setParentAdCode(parentAdCode);
            district.setName(name);
            district.setCenter(json.getStr("center"));
            district.setLevel(level);
            district.setCreateUserId(userId);
            district.setCreateTime(date);
            data.add(district);
            List<?> childrenList = (List<?>) json.get("districts");
            if (childrenList != null && !childrenList.isEmpty()) {
                data.addAll(getDistrictData(childrenList, adCode));
            }
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void init() {
        log.info(">>>>>>>>>> init district start");
        // 根据高德地图api获取省市区数据
        String url = "https://restapi.amap.com/v3/config/district?keywords=100000&subdistrict=3&key=255283ee36926848442372f33582aec3";
        HttpResponse response = HttpRequest.get(url).execute();
        if (response.getStatus() != HttpStatus.OK.value()) {
            throw new BusinessException("初始化失败，请求超时");
        }
        JSONObject body = JSONUtil.parseObj(response.body());
        if (!"1".equals(body.get("status"))) {
            throw new BusinessException(String.format("初始化失败，%s", body.getStr("info")));
        }
        List<?> list = (List<?>) body.get("districts");
        // 全国
        JSONObject country = (JSONObject) list.get(0);
        // 所有省
        List<?> provinceList = (List<?>) country.get("districts");
        List<BizDistrict> data = getDistrictData(provinceList, country.getStr("adcode"));
        // 清空表
        bizDistrictMapper.clearTable();
        // 批量插入
        bizDistrictMapper.batchInsert(data);
        // 清空缓存
        CacheUtils.delByPattern(RedisKeys.DISTRICT_DATA);
        log.info(">>>>>>>>>> init district finish");
    }

    @Override
    public List<District> findDistrict(District district) {
        long timeout = 3600L * 24;
        String key = RedisKeys.DISTRICT_DATA.concat(JSONUtil.toJsonStr(district));
        return CacheUtils.cacheAndGet(key, timeout, () -> bizDistrictMapper.findDistrict(district));
    }

}
