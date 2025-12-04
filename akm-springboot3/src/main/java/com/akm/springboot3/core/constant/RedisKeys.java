package com.akm.springboot3.core.constant;

/**
 * 将所有的redis key统一定义在此, 避免key四处分散, 导致后期维护redis时无法找到所有的key
 *
 * @author xiaojun
 *
 */
public final class RedisKeys {

    public static final String SEPARATOR = ":";
    /**
     * 项目中所有key的前缀, 避免不同项目访问同一个redis集群时出现重复key,
     * CacheUtils中的方法会自动加上此前缀, 调用CacheUtils, StringCacheUtils时key无需加此前缀
     */
    public static final String PREFIX = "akm" + SEPARATOR;
    /**
     * 互斥锁mutex的前缀, 完整的key为 PREFIX + NXKEY + key
     */
    public static final String NXKEY = "nx" + SEPARATOR;
    /**
     * 某角色对应的Api uri列表key前缀, 完整的key为 PREFIX + ROLE_URI + role_code
     */
    public static final String ROLE_URI = "role-uri" + SEPARATOR;
    /**
     * token前缀
     * 根据token可找到user信息
     */
    public static final String TOKEN = "token" + SEPARATOR;
    /**
     * userId和token关系前缀
     * 根据该key可以找到token
     */
    public static final String USER_ID_TOKEN = "userId-token" + SEPARATOR;
    /**
     * 验证码
     */
    public static final String CAPTCHA = "captcha" + SEPARATOR;
    /**
     * 密码错误次数
     */
    public static final String PASSWORD_ERRORS = "password-errors" + SEPARATOR;
    /**
     * rsa秘钥对
     */
    public static final String RSA_KEY_PAIR = "rsa-key-pair";
    /**
     * 从bing获取的背景图url https://blog.saintic.com/blog/240.html
     */
    public static final String BING_GB_URL = "bing-gb-url";
    /**
     * 接口请求频率缓存key
     */
    public static final String API_FREQ = "api-freq" + SEPARATOR;
    /**
     * 请求签名重复校验集合key
     */
    public static final String REQUEST_SIGN_SET = "request-sign-set";
    /**
     * 请求CSRF_TOKEN，防止表单重复提交
     */
    public static final String CSRF_TOKEN = "csrf-token" + SEPARATOR;
    /**
     * 省市区数据缓存key
     */
    public static final String DISTRICT_DATA = "district-data" + SEPARATOR;
    /**
     * OPT 缓存用户验证通过的编码
     */
    public static final String OPT_ALLOW_BTN_CODE = "opt-allow-btn_code" + SEPARATOR;
    /**
     * OPT 缓存用户使用过的code
     */
    public static final String OPT_USER_USED_CODE = "opt-user-used-code" + SEPARATOR;
    /**
     * 缓存全部组织机构
     */
    public static final String SYS_ORG_LIST = "sys-org" + SEPARATOR;
    /**
     * 缓存角色关联的组织机构
     */
    public static final String SYS_ROLE_ID_ORG_ID = "roleId-orgId" + SEPARATOR;

    private RedisKeys() {
        throw new IllegalStateException("RedisKeys Utility class");
    }
}
