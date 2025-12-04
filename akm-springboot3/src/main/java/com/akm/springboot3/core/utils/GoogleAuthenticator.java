package com.akm.springboot3.core.utils;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GoogleAuthenticator {

    /**
     * 发行人或应用名称或系统名称
     */
    static String ISSUER = "127.0.0.1:akm-framework";

    /**
     * 最多可偏移的时间窗口数
     */
    static int WINDOW_SIZE = 1;

    /**
     * 生成密钥
     *
     * @return 秘钥
     */
    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        // make the secret key more human-readable by lower-casing and
        // inserting spaces between each group of 4 characters
        return secretKey.toUpperCase();
    }


    /**
     * 生成验证码
     *
     * @param secretKey 秘钥
     * @return 一次性验证码
     */
    public static String getTotpCode(String secretKey) {
        String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(normalizedBase32Key);
        String hexKey = Hex.encodeHexString(bytes);
        long time = (System.currentTimeMillis() / 1000) / 30;
        String hexTime = Long.toHexString(time);
        return TOTP.generateTotp(hexKey, hexTime, "6");
    }

    /**
     * 生成二维码内容，形如：otpauth://totp/发行人%3A用户名?secret=秘钥&issuer=发行人
     * 前端自行实现生成二维码图片，使用Google Authenticator应用扫描二维码绑定秘钥
     *
     * @param secretKey 秘钥
     * @param account   用户名、用户信息
     * @return 二维码内容
     */
    public static String getQrCodeData(String secretKey, String account) {
        String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
        return "otpauth://totp/"
            + URLEncoder.encode(ISSUER + ":" + account, StandardCharsets.UTF_8)
            .replace("+", "%20")
            + "?secret="
            + URLEncoder.encode(normalizedBase32Key, StandardCharsets.UTF_8).replace(
            "+", "%20") + "&issuer="
            + URLEncoder.encode(ISSUER, StandardCharsets.UTF_8).replace("+", "%20");
    }


    /**
     * 检查用户输入的验证码是否有效
     *
     * @param secret 秘钥
     * @param code   一次性验证码
     * @return true验证码正确
     */
    public static boolean checkCode(String secret, long code) {
        long timeMsec = System.currentTimeMillis();
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        // convert unix msec time into a 30 second "window"
        // this is per the TOTP spec (see the RFC for details)
        long t = (timeMsec / 1000L) / 30L;
        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        for (int i = -WINDOW_SIZE; i <= WINDOW_SIZE; ++i) {
            long hash;
            try {
                hash = verifyCode(decodedKey, t + i);
            } catch (Exception e) {
                // Yes, this is bad form - but
                // the exceptions thrown would be rare and a static
                // configuration problem
                throw new RuntimeException(e.getMessage());
                // return false;
            }
            if (hash == code) {
                return true;
            }
        }
        // The validation code is invalid.
        return false;
    }

    private static int verifyCode(byte[] key, long t)
        throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }

    // public static void main(String[] args) {
    //     // 生成秘钥，形如：DVMJDGSA3ODOS6FNSC7QHNFYWAQEI6H2
    //     System.out.println(generateSecretKey());
    //
    //     // 生成二维码内容，用于APP扫码绑定秘钥
    //     System.out.println(getQrCodeData("DVMJDGSA3ODOS6FNSC7QHNFYWAQEI6H2", "18688498342"));
    //     //
    //     // 根据秘钥生成一次性验证码
    //     System.out.println(getTotpCode("DVMJDGSA3ODOS6FNSC7QHNFYWAQEI6H2"));
    //     //
    //     // 验证一次性验证码是否正确
    //     System.out.println(checkCode("DVMJDGSA3ODOS6FNSC7QHNFYWAQEI6H2", 856774));
    // }
}
