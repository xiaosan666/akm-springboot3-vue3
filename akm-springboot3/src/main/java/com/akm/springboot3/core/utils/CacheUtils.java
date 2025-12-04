package com.akm.springboot3.core.utils;

import com.akm.springboot3.core.constant.RedisKeys;
import com.akm.springboot3.core.utils.cache.CacheStorage;
import com.akm.springboot3.core.utils.cache.LocalCacheStorage;
import com.akm.springboot3.core.utils.cache.RedisCacheStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 缓存相关的帮助类, 提供了操作缓存的公用方法。
 * 此类中的方法基于RedisTemplate, 根据配置, key和value可以使用不同的序列化策略。
 *
 * @author xiaojun
 *
 */
@SuppressWarnings("unchecked")
public class CacheUtils {

    /**
     * 使用互斥锁进行缓存获取时的最大重试次数
     */
    public static final int MUTEX_MAX_RETRY = 5;
    /**
     * 互斥锁自动失效的时间(秒)
     */
    public static final long MUTEX_EXPIRE_SEC = 120L;
    /**
     * 互斥锁的默认值
     */
    public static final String MUTEX_VALUE = "1";
    private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);
    private static final String REDIS = "redis";
    private static final String CACHE_TYPE_CONFIG_KEY = "akm.cacheType";

    private static final CacheStorage<Object> cacheStorage;

    static {
        Environment env = SpringContextHolder.getBean(Environment.class);
        if (REDIS.equals(env.getProperty(CACHE_TYPE_CONFIG_KEY))) {
            cacheStorage = new RedisCacheStorage(SpringContextHolder.getBean(RedisTemplate.class));
            log.info(">> using REDIS as cache.");
        } else {
            cacheStorage = new LocalCacheStorage<>();
            log.info(">> using LOCAL memory as cache.");
        }
    }

    /**
     * Don't let anyone instantiate this class
     */
    private CacheUtils() {
    }

    /**
     * 从缓存里获取对应key的值。
     *
     * @param key 无需PREFIX前缀,方法里会自动加上
     * @param <T> 缓存值的类型
     * @return 缓存中的值，如果不存在则返回null
     */
    public static <T> T get(String key) {
        return (T) cacheStorage.get(prefixKey(key));
    }


    /**
     * 从缓存里获取对应key的值（不添加前缀）
     */
    public static <T> T getNoPrefix(String key) {
        return (T) cacheStorage.get(key);
    }

    /**
     * 删除指定的缓存<br/>
     * 注意: 如果RedisTemplate配置的key序列化策略不是StringRedisSerializer, 使用此方法删除StringRedisTemplate设置的key会失败
     *
     * @param key 无需PREFIX前缀,方法里会自动加上
     */
    public static void del(String key) {
        cacheStorage.delete(prefixKey(key));
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
            return cacheStorage.keys(prefixKey(pattern));
        }
        return Collections.emptySet();
    }

    /**
     * 根据正则表达式删除所有匹配到的缓存
     *
     * @param pattern
     */
    public static void delByPattern(String pattern) {
        Set<String> keys = keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            cacheStorage.delete(keys);
        }
    }

    /**
     * 批量删除缓存
     *
     * @param keys 无需PREFIX前缀,方法里会自动加上
     */
    public static void batchDel(String... keys) {
        if (keys != null && keys.length > 0) {
            List<String> keyList = new ArrayList<>();
            for (String key : keys) {
                if (StringUtils.isNotBlank(key)) {
                    keyList.add(prefixKey(key));
                }
            }
            cacheStorage.delete(keyList);
        }
    }

    /**
     * 设置缓存值
     *
     * @param key   无需PREFIX前缀,方法里会自动加上
     * @param value
     */
    public static void set(String key, Object value) {
        cacheStorage.set(prefixKey(key), value);
    }

    /**
     * 设置缓存值并指定失效时间
     *
     * @param key     无需PREFIX前缀,方法里会自动加上
     * @param value
     * @param timeout 失效时间(秒)
     */
    public static void set(String key, Object value, long timeout) {
        cacheStorage.set(prefixKey(key), value, timeout, TimeUnit.SECONDS);
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
     * @param key         无需PREFIX前缀,方法里会自动加上
     * @param ttlSecs     若值为空, 从数据库获取值后回设时的失效秒数, 设置为 -1或0时表示永不失效
     * @param valueLoader 当缓存里数据不存在时, 获取数据的过程, 一般为从数据库获取
     * @param <T>         key对应的数据值的类型
     * @return 缓存中的值或从valueLoader获取的值
     */
    public static <T> T cacheAndGet(String key, long ttlSecs, Supplier<T> valueLoader) {
        String redisKey = prefixKey(key);
        T value = (T) cacheStorage.get(redisKey);
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
        if (setnx(nxkey, MUTEX_VALUE, MUTEX_EXPIRE_SEC)) {
            // 从database获取值
            value = valueLoader.get();
            // 注: 就算值是null，也设置此值, 避免没有这个key导致每次读取都要进入DB
            if (ttlSecs <= 0) {
                cacheStorage.set(redisKey, value);
            } else {
                cacheStorage.set(redisKey, value, ttlSecs, TimeUnit.SECONDS);
            }
            if (log.isDebugEnabled()) {
                log.debug(">> db value loaded, releasing mutex lock, key: {}", key);
            }
            // 释放互斥锁并返回值
            del(nxkey);
            return value;
        } else { // 获取互斥锁失败, 说明另一个线程已经在进行load db
            if (reachMaxRetryCount(key)) {
                log.warn(">> 获取缓存值时重试次数过多, key: {}", key);
                return null;
            }
            try {
                // 等待一段时间后再重试获取缓存值
                Thread.sleep(300);
                return cacheAndGet(key, ttlSecs, valueLoader);
            } catch (InterruptedException e) {
                log.error("获取缓存值失败", e);
                Thread.currentThread().interrupt();
            }
            return null;
        }

    }

    /**
     * Set if not exists, 只有缓存不存在的时候才设置。
     *
     * @param key   无需PREFIX前缀,方法里会自动加上
     * @param value 缓存值
     * @return 如果设置成功返回true，否则返回false
     */
    public static boolean setnx(String key, Object value) {
        return setnx(key, value, -1);
    }

    /**
     * SETNX，是「SET if Not eXists」的缩写，也就是只有不存在的时候才设置，可以利用它来实现锁的效果。
     *
     * @param key     无需PREFIX前缀,方法里会自动加上
     * @param value   缓存值
     * @param timeout 失效时间(秒)
     * @return 如果设置成功返回true，否则返回false
     */
    public static boolean setnx(String key, Object value, long timeout) {
        String nxkey = prefixKey(key);
        boolean flag = cacheStorage.setIfAbsent(nxkey, value);
        if (flag && timeout > 0) {
            cacheStorage.expire(nxkey, timeout, TimeUnit.SECONDS);
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
        String nxkey = prefixKey(key);
        return cacheStorage.expire(nxkey, timeout, unit);
    }

    /**
     * 获取缓存还有多久失效
     *
     * @param key 缓存键
     * @return 剩余生存时间（秒），如果key不存在返回-2，没有设置过期时间返回-1
     */
    public static Long getExpire(String key) {
        String nxkey = prefixKey(key);
        return cacheStorage.getExpire(nxkey);
    }

    /**
     * 获取缓存服务器的系统时间
     *
     * @return 系统时间（毫秒）
     */
    public static long time() {
        return cacheStorage.time();
    }

    /**
     * 为redis key加上与本项目相关的前缀, 避免不同程序访问同一个redis集群时出现的key重复问题
     */
    public static String prefixKey(String key) {
        return RedisKeys.PREFIX.concat(key);
    }

    /**
     * 在利用分布式锁进行获取缓存值时, 为了避免无限循环, 设定一个重试次数上限, 保存在线程上下文
     */
    public static boolean reachMaxRetryCount(String key) {
        String countKey = "cache-retry.".concat(key);
        Integer count = ThreadContext.get(countKey, 0);
        if (log.isDebugEnabled()) {
            log.debug(">> retry count: {}", count);
        }
        if (count > MUTEX_MAX_RETRY) {
            return true;
        } else {
            ThreadContext.set(countKey, count + 1);
            return false;
        }
    }

}
