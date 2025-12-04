package com.akm.springboot3.core.config;

import cn.hutool.core.lang.Dict;
import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.utils.CacheUtils;
import com.akm.springboot3.core.utils.EncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class InitializeResource implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitializeResource.class);

    @Value("${server.port}")
    private String serverPort;

    @Value("${akm.force-generate-rsa-key}")
    private Boolean forceGenerateRsaKey;

    @Override
    public void run(ApplicationArguments args) {
        printSwaggerAddress();
        generateRsaKeyPair();
    }

    /**
     * 输出swagger api地址
     */
    private void printSwaggerAddress() {
        log.info("start successfully!!!");
        log.info("http://localhost:{}/doc.html", serverPort);
        log.info("http://localhost:{}/druid/index.html", serverPort);
    }

    /**
     * 生成秘钥对放到缓存中
     * privateKey1和publicKey2用于后台解密和加密
     * privateKey2和publicKey1给前端解密和加密
     */
    private void generateRsaKeyPair() {
        if (Boolean.TRUE.equals(forceGenerateRsaKey) || CacheUtils.get(RedisKeys.RSA_KEY_PAIR) == null) {
            Dict rsaKeyPair1 = EncryptUtils.generateRsaKeyPair();
            String privateKey1 = rsaKeyPair1.getStr("privateKey");
            String publicKey1 = rsaKeyPair1.getStr("publicKey");
            Dict rsaKeyPair2 = EncryptUtils.generateRsaKeyPair();
            String privateKey2 = rsaKeyPair2.getStr("privateKey");
            String publicKey2 = rsaKeyPair2.getStr("publicKey");
            Dict dict = Dict.create().set("privateKey1", privateKey1)
                .set("publicKey1", publicKey1)
                .set("privateKey2", privateKey2)
                .set("publicKey2", publicKey2);
            CacheUtils.set(RedisKeys.RSA_KEY_PAIR, dict);
        }
    }

}
