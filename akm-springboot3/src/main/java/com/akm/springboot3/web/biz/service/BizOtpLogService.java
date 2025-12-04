package com.akm.springboot3.web.biz.service;

import com.akm.springboot3.web.biz.entity.BizOtpLog;

public interface BizOtpLogService {
    void asyncInsert(BizOtpLog record);

    int insert(BizOtpLog record);

    String getQrCodeData();

    void verificationOtpCode(String code, String btnCode);

    /**
     * 判断访问接口是否为白名单
     *
     * @param btnCode       与前端按钮编码一致
     * @param operationName 操作名称
     * @return 如果是白名单返回true，否则返回false
     */
    boolean assertOtpSecret(String btnCode, String operationName);
}





