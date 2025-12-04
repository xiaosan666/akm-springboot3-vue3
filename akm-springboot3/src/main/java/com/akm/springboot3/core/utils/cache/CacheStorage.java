package com.akm.springboot3.core.utils.cache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public interface CacheStorage<V> {

    /**
     * 获取key对应的缓存值
     *
     * @param key 缓存键
     * @return key对应的缓存值
     */
    V get(String key);

    /**
     * 删除指定key的缓存
     *
     * @param key 缓存键
     */
    void delete(String key);

    /**
     * 判断指定的key是否存在
     *
     * @param key 缓存键
     * @return 若缓存中有此key, 则返回{@code true}
     */
    boolean hasKey(String key);

    /**
     * 根据正则表达模版, 获取符合的key列表
     *
     * @param pattern 匹配模式
     * @return 匹配的key集合
     */
    Set<String> keys(String pattern);

    /**
     * 批量删除集合里指定的缓存
     *
     * @param keys 缓存键集合
     */
    void delete(Collection<String> keys);

    /**
     * 设置key对应的缓存值
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    void set(String key, V value);

    /**
     * 设置key对应的缓存, 并指定失效时间
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    void set(String key, V value, long timeout, TimeUnit unit);

    /**
     * Set key to hold the value if key is absent
     *
     * @param key   缓存键
     * @param value 缓存值
     * @return {@code true} if value is set successfully
     */
    boolean setIfAbsent(String key, V value);

    /**
     * set time to live for given key
     *
     * @param key     缓存键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return {@code true} if set successfully
     */
    boolean expire(String key, long timeout, TimeUnit unit);


    /**
     * 获取缓存还有多久失效
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     * 否则，以秒为单位，返回 key 的剩余生存时间。
     *
     * @param key 缓存键
     * @return 剩余生存时间（秒），如果key不存在返回-2，没有设置过期时间返回-1
     */
    Long getExpire(String key);

    /**
     * 获取缓存服务器的系统时间
     *
     * @return 系统时间（毫秒）
     */
    long time();

    /**
     * Redis 集合(Set)
     * 设置setName对应的缓存, 并指定失效时间
     *
     * @param setName 集合(Set)名称
     * @param value   缓存值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    void setSet(String setName, V value, long timeout, TimeUnit unit);

    /**
     * Redis 集合(Set)
     * 判断集合(Set)是否存在value
     *
     * @param setName 集合(Set)名称
     * @param value   value
     * @return 如果存在返回{@code true}，否则返回{@code false}
     */
    Boolean isMemberBySet(String setName, V value);
}
