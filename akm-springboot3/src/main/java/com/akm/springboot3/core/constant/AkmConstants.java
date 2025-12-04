package com.akm.springboot3.core.constant;

/**
 * 项目中用到的一些常量，注意和RedisKeys区分
 *
 * @author xiaojun
 *
 */
public final class AkmConstants {

    /**
     * springboot配置文件属性加密salt
     * 查看：EncryptUtils.encryptSpringBootConfiguration
     */
    public static final String ENCRYPT_SPRINGBOOT_CONFIGURATION_SALT = "275eb45a0ecf1bb31491958910518176";
    /**
     * 用户登陆的token
     */
    public static final String TOKEN = "token";
    /**
     * 用户的访问类型（客户端类型）
     */
    public static final String CLIENT_TYPE = "clientType";
    /**
     * 用户id
     */
    public static final String USER_ID = "userId";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 用户手机号码
     */
    public static final String PHONE = "phone";
    /**
     * 用户姓名
     */
    public static final String REALNAME = "realname";
    /**
     * 用户角色列表
     */
    public static final String ROLE_IDS = "roleIds";
    /**
     * token请求头Authorization
     */
    public static final String HTTP_HEADER_TOKEN = "Authorization";
    /**
     * 请求头aes-key
     */
    public static final String HTTP_HEADER_AES_KEY = "k";
    /**
     * 请求头时间戳
     */
    public static final String HTTP_HEADER_TIMESTAMP = "t";
    /**
     * 请求头签名
     */
    public static final String HTTP_HEADER_SIGN = "s";
    /**
     * 请求头随机数
     */
    public static final String HTTP_HEADER_RANDOM = "r";
    /**
     * 请求头p参数用于和随机数结合计算加密key
     */
    public static final String HTTP_HEADER_P = "p";
    /**
     * 请求CSRF_TOKEN，防止表单重复提交
     */
    public static final String HTTP_HEADER_CSRF_TOKEN = "csrf-token";
    /**
     * 请求id
     */
    public static final String HTTP_HEADER_REQ_ID = "reqId";
    /**
     * 接口包含字符串
     */
    public static final String OPEN_API = "/open/";
    public static final String PUBLIC_API = "/public/";
    public static final String VIEW_API = "/view/";
    public static final String OP_API = "/op/";

    private AkmConstants() {
        throw new IllegalStateException("AkmConstants Utility class");
    }
}
