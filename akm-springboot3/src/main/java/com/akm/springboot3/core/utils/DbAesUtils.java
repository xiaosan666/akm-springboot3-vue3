package com.akm.springboot3.core.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * MySql AES加密工具类
 *
 * @author xiaojun
 * <p>
 * 加密：
 * MySql：SELECT HEX(AES_ENCRYPT('张三','12345678'));
 * Java： DbAesUtils.encrypt('张三','12345678')));
 * 解密：
 * MySql：SELECT AES_DECRYPT(UNHEX('3FCEE11103DC1438CC347E4A52AF2FA8'),'12345678');
 * Java： DbAesUtils.decrypt('3FCEE11103DC1438CC347E4A52AF2FA8','12345678')));
 */
@Component
@Getter
public class DbAesUtils {
    /**
     * 加密时显示的文本
     */
    public static final String CIPHER_TEXT = "******";
    /**
     * 密钥，默认从配置文件读取，可以在应用启动时指定
     */
    public static String secret;

    /**
     * 使用默认密钥加密
     */
    public static String encrypt(String content) {
        return EncryptUtils.mysqlAesEncrypt(content, secret);
    }

    /**
     * 使用默认密钥解密
     */
    public static String decrypt(String content) {
        return EncryptUtils.mysqlAesDecrypt(content, secret);
    }

    @Value("${dbAesKey}")
    public synchronized void setSecret(String val) {
        secret = val;
    }
}
