package com.akm.springboot3.core.utils;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.utils.cache.CacheStorage;
import com.akm.springboot3.core.utils.cache.LocalCacheStorage;
import com.akm.springboot3.core.utils.cache.StringRedisCacheStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存相关的帮助类, 提供了操作缓存的公用方法。
 * 此类中的方法基于StringRedisTemplate, key和value都采用StringRedisSerializer序列化策略。
 *
 * @author xiaojun
 *
 */
public class StringCacheUtils {

    private static final Logger log = LoggerFactory.getLogger(StringCacheUtils.class);
    private static final String REDIS = "redis";
    private static final String CACHE_TYPE_CONFIG_KEY = "akm.cacheType";
    private static final CacheStorage<String> cacheStorage;

    static {
        Environment env = SpringContextHolder.getBean(Environment.class);
        if (REDIS.equals(env.getProperty(CACHE_TYPE_CONFIG_KEY))) {
            cacheStorage = new StringRedisCacheStorage(SpringContextHolder.getBean(StringRedisTemplate.class));
            log.info(">> using REDIS as cache.");
        } else {
            cacheStorage = new LocalCacheStorage<>();
            log.info(">> using LOCAL memory as cache.");
        }
    }

    /**
     * Don't let anyone instantiate this class
     */
    private StringCacheUtils() {
    }

    /**
     * 从缓存中获取整数值
     *
     * @param key 无需前缀,方法里会自动加上
     * @return 整数值，如果不存在或格式错误则返回null
     */
    public static Integer getInt(String key) {
        String value = cacheStorage.get(CacheUtils.prefixKey(key));
        return StringUtils.isBlank(value) ? null : Integer.valueOf(value);
    }

    /**
     * 从缓存中获取字符串值
     *
     * @param key 无需前缀,方法里会自动加上
     * @return 字符串值，如果不存在则返回null
     */
    public static String get(String key) {
        return cacheStorage.get(CacheUtils.prefixKey(key));
    }

    /**
     * 从缓存里获取对应key的值（不添加前缀）
     */
    public static String getNoPrefix(String key) {
        return cacheStorage.get(key);
    }

    /**
     * @param key   无需前缀,方法里会自动加上
     * @param value
     */
    public static void set(String key, String value) {
        cacheStorage.set(CacheUtils.prefixKey(key), value);
    }

    /**
     * 设置缓存值，并指定失效时间
     *
     * @param key     无需前缀,方法里会自动加上
     * @param value
     * @param timeout 失效时间(秒)
     */
    public static void set(String key, String value, long timeout) {
        set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存值，并指定失效时间
     *
     * @param key     无需前缀,方法里会自动加上
     * @param value
     * @param timeout
     * @param unit
     */
    public static void set(String key, String value, long timeout, TimeUnit unit) {
        cacheStorage.set(CacheUtils.prefixKey(key), value, timeout, unit);
    }

    /**
     * 删除指定的缓存
     *
     * @param key 无需前缀,方法里会自动加上
     */
    public static void del(String key) {
        cacheStorage.delete(CacheUtils.prefixKey(key));
    }

    /**
     * 删除指定的缓存（不添加前缀）
     */
    public static void delNoPrefix(String key) {
        cacheStorage.delete(key);
    }

    /**
     * 按照正则表达式获取所有匹配到的key
     *
     * @param pattern 无需PREFIX前缀,方法里会自动加上
     * @return 匹配到的key集合
     */
    public static Set<String> keys(String pattern) {
        if (StringUtils.isNotBlank(pattern)) {
            return cacheStorage.keys(CacheUtils.prefixKey(pattern));
        }
        return Collections.emptySet();
    }

    /**
     * 判断key是否已存在
     *
     * @param key 缓存键
     * @return 如果存在返回true，否则返回false
     */
    public static boolean hasKey(String key) {
        return cacheStorage.hasKey(CacheUtils.prefixKey(key));
    }

    /**
     * set if not exist, 不存在时才会设置
     *
     * @param key   无需前缀,方法里会自动加上
     * @param value 缓存值
     * @return 如果设置成功返回true，否则返回false
     */
    public static boolean setnx(String key, String value) {
        return setnx(key, value, -1);
    }

    /**
     * set if not exist, 不存在时才会设置, 并指定失效时间(秒)
     *
     * @param key   无需前缀,方法里会自动加上
     * @param value 缓存值
     * @param ttl   大于0才起作用
     * @return 如果设置成功返回true，否则返回false
     */
    public static boolean setnx(String key, String value, long ttl) {
        String nxkey = CacheUtils.prefixKey(key);
        boolean flag = cacheStorage.setIfAbsent(nxkey, value);
        if (flag && ttl > 0) {
            cacheStorage.expire(nxkey, ttl, TimeUnit.SECONDS);
        }
        return flag;
    }

    /**
     * 设置缓存的失效时间
     *
     * @param key     缓存键
     * @param timeout 失效时间(秒)
     * @return 如果设置成功返回true，否则返回false
     */
    public static Boolean expire(String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存的失效时间
     *
     * @param key     缓存键
     * @param timeout 失效时间
     * @param unit    时间单位
     * @return 如果设置成功返回true，否则返回false
     */
    public static Boolean expire(String key, final long timeout, final TimeUnit unit) {
        String redisKey = CacheUtils.prefixKey(key);
        return cacheStorage.expire(redisKey, timeout, unit);
    }

    /**
     * 从缓存获取对应key的值, 若缓存里没有, 则从 valueLoader 获取, 并回设到缓存里.
     * <p>
     * 此方法增加了保护策略，使用一个简单的分布式锁(互斥锁mutex)防止缓存被击穿。简单地来说，就是在缓存失效的时候（判断拿出来的值为空），
     * 不是立即去load db，而是先使用缓存工具的某些带成功操作返回值的操作（比如Redis的SETNX或者Memcache的ADD）去set一个mutex key，
     * 当操作返回成功时，再进行load db的操作并回设缓存；否则，就重试整个get缓存的方法。
     * </p>
     * <p>
     * 缓存击穿: 缓存在某个时间点过期的时候，恰好在这个时间点对这个Key有大量的并发请求过来，
     * 这些请求发现缓存过期一般都会从后端DB加载数据并回设到缓存，这个时候大并发的请求可能会瞬间把后端DB压垮。
     * </p>
     *
     * @param key         无需前缀,方法里会自动加上
     * @param timeout     将值回设到缓存时的失效时间(秒), 设置为-1或0表示永不失效
     * @param valueLoader 缓存值不存在时,获取该值的方法
     * @return 缓存中的值或从valueLoader获取的值
     */
    public static String cacheAndGet(String key, long timeout, Supplier<String> valueLoader) {
        String redisKey = CacheUtils.prefixKey(key);
        String value = cacheStorage.get(redisKey);
        if (value != null) {
            return value;
        }
        // 有可能此缓存值就是null, 所以先检查是否有key
        if (cacheStorage.hasKey(redisKey)) {
            return null;
        }
        // 每个key都有自己的nx锁
        String nxkey = RedisKeys.NXKEY.concat(key);
        // 设置互斥锁的超时时间, 防止del操作失败导致缓存过期后一直不能load db
        if (setnx(nxkey, CacheUtils.MUTEX_VALUE, CacheUtils.MUTEX_EXPIRE_SEC)) {
            // 从database获取值
            value = valueLoader.get();
            // 注: 就算值是null，也设置此值, 避免没有这个key导致每次读取都要进入DB
            if (timeout <= 0) {
                cacheStorage.set(redisKey, value);
            } else {
                cacheStorage.set(redisKey, value, timeout, TimeUnit.SECONDS);
            }
            // 释放互斥锁并返回值
            del(nxkey);
            return value;
        } else { // 获取互斥锁失败, 说明另一个线程已经在进行load db
            if (CacheUtils.reachMaxRetryCount(key)) {
                log.error(">> 获取缓存值时重试次数过多, key: {}", key);
                return null;
            }
            try {
                // 等待一段时间后再重试获取缓存值
                Thread.sleep(200);
                return cacheAndGet(key, timeout, valueLoader);
            } catch (InterruptedException e) {
                log.error("获取缓存值失败", e);
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }

    /**
     * Redis 集合(Set)
     * 设置setName对应的缓存, 并指定失效时间
     *
     * @param setName 集合(Set)名称
     * @param value
     * @param timeout
     */
    public static void setSet(String setName, String value, long timeout) {
        cacheStorage.setSet(CacheUtils.prefixKey(setName), value, timeout, TimeUnit.SECONDS);
    }

    /**
     * Redis 集合(Set)
     * 判断集合(Set)是否存在value
     *
     * @param setName 集合(Set)名称
     * @param value   value
     * @return 如果存在返回true，否则返回false
     */
    public static Boolean isMemberBySet(String setName, String value) {
        return cacheStorage.isMemberBySet(CacheUtils.prefixKey(setName), value);
    }
}
