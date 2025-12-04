package com.akm.springboot3.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

/**
 * 加密工具类
 *
 * @author xiaojun
 *
 */
public class EncryptUtils {

    /**
     * AES加密
     */
    public static String aesEncrypt(String content, String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, keyBytes, keyBytes);
        return aes.encryptBase64(content);
    }

    /**
     * AES解密
     */
    public static String aesDecrypt(String content, String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding, keyBytes, keyBytes);
        return aes.decryptStr(content, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * RSA 公钥加密
     */
    public static String rsaEncrypt(String content, String publicKey) {
        RSA rsa = new RSA(null, publicKey);
        return rsa.encryptBase64(content, KeyType.PublicKey);
    }

    /**
     * RSA 私钥解密
     */
    public static String rsaDecrypt(String content, String privateKey) {
        RSA rsa = new RSA(privateKey, null);
        return rsa.decryptStr(content, KeyType.PrivateKey);
    }

    /**
     * 生成rsa秘钥对
     */
    public static Dict generateRsaKeyPair() {
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        String privateKey = Base64.encode(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.encode(keyPair.getPublic().getEncoded());
        return Dict.create().set("privateKey", privateKey).set("publicKey", publicKey);
    }


    /**
     * 消息摘要算法 sha256
     */
    public static String sha256(String content) {
        return SecureUtil.sha256(content);
    }

    /**
     * 消息摘要算法 HmacSHA256
     */
    public static String hmacSha256(String content, String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        HMac mac = new HMac(HmacAlgorithm.HmacSHA256, keyBytes);
        return mac.digestHex(content);
    }

    /**
     * 字符串base64编码
     */
    public static String base64Encode(String content) {
        return Base64.encode(content);
    }

    /**
     * 字符串base64解码
     */
    public static String base64Decode(String content) {
        return Base64.decodeStr(content);
    }

    /**
     * 生成系统登录密码
     */
    public static String getPassword(String unencryptedPassword, String salt, String userId) {
        if (StringUtils.isBlank(salt)) {
            return unencryptedPassword;
        }
        return hmacSha256(unencryptedPassword + userId, salt);
    }

    /**
     * 登录密码盐
     */
    public static String getSalt() {
        return IdUtil.simpleUUID();
    }

    /**
     * 国密SM4加密
     *
     * @param content 待加密的内容
     * @param key     密钥，长度为16位
     * @return 加密后的字符串
     */
    public static String sm4Encrypt(String content, String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SymmetricCrypto sm4 = SmUtil.sm4(keyBytes);
        return sm4.encryptBase64(content);
    }

    /**
     * 国密SM4解密
     *
     * @param content 待加密的内容
     * @param key     密钥，长度为16位
     * @return 解密后字符串
     */
    public static String sm4Decrypt(String content, String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SymmetricCrypto sm4 = SmUtil.sm4(keyBytes);
        return sm4.decryptStr(content);
    }


    /**
     * mysql AES加解密key没有长度限制，java通过此方法补充key长度
     *
     * @param key key参数
     * @return 密钥
     */
    private static SecretKeySpec generateMySqlAesKey(final String key) {
        final byte[] finalKey = new byte[16];
        int i = 0;
        for (byte b : key.getBytes(StandardCharsets.UTF_8)) {
            finalKey[i++ % 16] ^= b;
        }
        return new SecretKeySpec(finalKey, SymmetricAlgorithm.AES.getValue());
    }

    /**
     * mysql-java通用AES加密
     * MySql：SELECT HEX(AES_ENCRYPT('张三','12345678'));
     * Java： EncryptUtils.mysqlAesEncrypt('张三','12345678')));
     *
     * @param content 待加密内容
     * @param key     密钥
     * @return 加密结果
     */
    public static String mysqlAesEncrypt(String content, String key) {
        if (content == null) {
            return null;
        }
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, generateMySqlAesKey(key));
        return symmetricCrypto.encryptHex(content).toUpperCase();
    }

    /**
     * mysql-java通用AES解密
     * MySql：SELECT AES_DECRYPT(UNHEX('3FCEE11103DC1438CC347E4A52AF2FA8'),'12345678');
     * Java： EncryptUtils.mysqlAesDecrypt('3FCEE11103DC1438CC347E4A52AF2FA8','12345678')));
     *
     * @param content 待解密内容
     * @param key     密钥
     * @return 解密结果
     */
    public static String mysqlAesDecrypt(String content, String key) {
        if (content == null) {
            return null;
        }
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES, generateMySqlAesKey(key));
        return symmetricCrypto.decryptStr(content);
    }

    /**
     * 生成springboot配置文件属性加密结果，如数据库密码加密
     *
     * @param salt // 随机数，用做盐
     * @param data // 加密原文,如数据库真实密码
     */
    public static String encryptSpringBootConfiguration(String salt, String data) {
        return getEncryptor(salt).encrypt(data);
    }

    public static String decryptSpringBootConfiguration(String salt, String encryptedData) {
        return getEncryptor(salt).decrypt(encryptedData);
    }

    public static PooledPBEStringEncryptor getEncryptor(String salt) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(salt);
        config.setPoolSize("1");
        encryptor.setConfig(config);
        return encryptor;
    }

    /*public static void main(String[] args) {
        // aes加解密
        String content = "akm-hello";
        String aesKey = "0123456789abcdef";
        String aesEncrypt = EncryptUtils.aesEncrypt(content, aesKey);
        log.info("aesEncrypt:{}", aesEncrypt);
        String aesDecrypt = EncryptUtils.aesDecrypt(aesEncrypt, aesKey);
        log.info("aesDecrypt:{}", aesDecrypt);

        // 生成rsa秘钥对
        Dict keyPair = generateRsaKeyPair();
        log.info("keyPair:{}", keyPair);

        // rsa公钥加密私钥解密
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLEY+QIdWgdKAeol5zyngqMPUXdmUPjvaAdG5rIzEe31OMrcvB/hXBXM9M7au/jXF5/mJk0jORbJnYFf0SjIbpCidUbtrQa5zAWfuIwD9YnIRP3wyf/Erd3ft3Z2FZC2byECKLiC/rTTu/y9YiLvcaVJmeYlUzUbrGBa9ZSjASfwIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIsRj5Ah1aB0oB6iXnPKeCow9Rd2ZQ+O9oB0bmsjMR7fU4yty8H+FcFcz0ztq7+NcXn+YmTSM5FsmdgV/RKMhukKJ1Ru2tBrnMBZ+4jAP1ichE/fDJ/8St3d+3dnYVkLZvIQIouIL+tNO7/L1iIu9xpUmZ5iVTNRusYFr1lKMBJ/AgMBAAECgYEAhPyrC8q/Ley4/fKJXjM9DCO7DM/EPseYxCgTO1Lw8zPfAsVj90QR69Hjzw3s0oXhAScZUo6Mj8GJsND+LFHD4/42ShRh/nViNlLoApoi2ja110ewJWAsc7Vrx5HS5dEW4Lu1hG2ELHkvbNuRxSOJl7WsloHY5A/WB8tdiojlvikCQQDsMdFAWsif2NZwnC63/OQmHQGQK0shZ0Qtdnv0rrBYjisX/EMwOXwBxcoQjG4id47Tv+jVZDJChE69oB9TghArAkEAlrrTFx5IAPaafnWBdVjXqQCE/lvA3VlNCtuAvuHMn+xMoF+yxPvv4jDyVUI5pl/oLswfUCMfB+ihke6hQ05I/QJATrQy8+zMWWdZ3WBNuQVNyN11feLPQCw27adDmr7zCC8ncPCgOtuM1NJhiK4irN2F/E1AeorOrJeF3W4TwjHezQJAdRpIjZl1D2qHtN/N6EFRq3c/2+2k9VM/y8OlTqdWtph7H825sdiaEf09CIgfCedRF6p2WWgv35EYlavzlbrocQJBAJxCh7jDJrnwQ8nnIbKc/D3SRMF5jEAJeLdaq9l1dzXpisRhFu/xl23EIwPBNdKTlSXnDNSM3HTVcUwdsyiotqA=";
        String rsaEncrypt = EncryptUtils.rsaEncrypt(content, publicKey);
        log.info("rsaEncrypt:{}", rsaEncrypt);
        String rsaDecrypt = EncryptUtils.rsaDecrypt(rsaEncrypt, privateKey);
        log.info("rsaDecrypt:{}", rsaDecrypt);

        // 生成配置文件属性加密结果，如数据库密码加密
        String salt = IdUtil.simpleUUID();
        String data = "hello";
        String result = encryptSpringBootConfiguration(salt, data);
        log.info("encryptSpringBootConfiguration：data:{},salt:{},result:{}", data, salt, result);

        // 生成用户登录密码
        String unencryptedPw = EncryptUtils.sha256("hello@1234");
        String pSalt = "6e43cfb7e9b321b1ef84dab106b214df";
        String userId = "1283226473069371392";
        String encryptedPassword = EncryptUtils.getPassword(unencryptedPw, pSalt, userId);
        log.info("encryptedPassword:{}", encryptedPassword);
    }*/
}
