package cn.fanzy.atfield.cache;

import cn.hutool.core.util.StrUtil;

import java.util.concurrent.TimeUnit;

/**
 * 缓存服务
 *
 * @author fanzaiyang
 * @date 2023/11/29
 */
public interface CacheService {
    String PREFIX = "global_infra_cache:";

    /**
     * 获取密钥名称
     *
     * @param key 钥匙
     * @return {@link String}
     */
    default String getKeyName(String key) {
        return StrUtil.format("{}{}", PREFIX, key);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return 缓存值
     */
    Object get(String key);

    /**
     * 收到
     *
     * @param key      钥匙
     * @param isUpdate 是更新
     * @return {@link Object}
     */
    Object get(String key, boolean isUpdate);

    /**
     * 获取缓存
     *
     * @param key   缓存key
     * @param clazz 缓存值类型
     * @param <T>   缓存值类型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 收到
     *
     * @param key      钥匙
     * @param clazz    clazz
     * @param isUpdate 是更新
     * @return {@link T}
     */
    <T> T get(String key, Class<T> clazz, boolean isUpdate);

    /**
     * 获取缓存后删除
     *
     * @param key 缓存key
     * @return 缓存值
     */
    Object pop(String key);

    /**
     * 获取缓存后删除
     *
     * @param key   缓存key
     * @param clazz 缓存值类型
     * @param <T>   缓存值类型
     * @return 缓存值
     */
    <T> T pop(String key, Class<T> clazz);

    /**
     * 缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void put(String key, Object value);

    /**
     * 放
     *
     * @param key    钥匙
     * @param value  价值
     * @param expire 失效时间（毫秒）
     */
    void put(String key, Object value, long expire);

    /**
     * 放
     *
     * @param key      钥匙
     * @param value    价值
     * @param expire   失效
     * @param timeUnit 时间单位
     */
    void put(String key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 删除缓存
     *
     * @param key 钥匙
     */
    void remove(String key);

    /**
     * 清除所有缓存
     */
    void clear();
}
