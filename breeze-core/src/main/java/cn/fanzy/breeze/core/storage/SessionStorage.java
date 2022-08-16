package cn.fanzy.breeze.core.storage;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>
 * 本地线程存储工具类
 * </p>
 * 该工具主要是一个基于内存和ThreadLocal的KV键值对存储工具，该键值对内容与当前线程关联，在使用时需要注意内存溢出问题。<strong>接口均为线程安全的</strong>
 *
 * @author fanzaiyang
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class SessionStorage {
    /**
     * 本地线程存储
     */
    private static final ThreadLocal<Map<String, Object>> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 根据键获取存储的值
     *
     * @param key 存储的键
     * @return 存储的值
     */
    public synchronized static Object get(String key) {
        Assert.notNull(key, "key值不能为空");
        Map<String, Object> map = get();
        return map.get(key);
    }

    /**
     * 根据存储的数据的类型获取存储的数据
     *
     * @param <T>   存储的数据的类型
     * @param clazz 存储的数据的类型
     * @return 存储的数据
     */
    @SuppressWarnings("unchecked")
    public synchronized static <T> T get(Class<T> clazz) {
        Assert.notNull(clazz, "key值不能为空");
        try {
            return (T) get(clazz.getName());
        } catch (Exception e) {
            log.info("【公共工具】获取存储的数据时出现问题，原因：{}", e.getMessage());
        }
        return null;

    }

    /**
     * 根据存储的键获取存储的数据，然后清楚当前存储的数据
     *
     * @param key 存储的键
     * @return 存储的数据的类型
     */
    public synchronized static Object pop(String key) {
        try {
            return get(key);
        } finally {
            clear();
        }
    }

    /**
     * 根据存储的数据的类型获取存储的数据，然后清楚当前存储的数据
     *
     * @param <T>   存储的数据的类型
     * @param clazz 存储的数据的类型
     * @return 存储的数据
     */
    public synchronized static <T> T pop(Class<T> clazz) {
        try {
            return get(clazz);
        } finally {
            clear();
        }
    }

    /**
     * 存入一个数据，存储的键为当前数据的类型，值为当前数据
     *
     * @param <T> 存储的数据的类型
     * @param t   存储的数据
     */
    public synchronized static <T> void put(T t) {
        Assert.notNull(t, "存储的值不能为空");
        put(t.getClass().getName(), t);
    }

    /**
     * 向本地线程副本里存储信息
     *
     * @param key   信息的键
     * @param value 信息的值
     */
    public synchronized static void put(String key, Object value) {
        Assert.notNull(key, "key值不能为空");
        Map<String, Object> map = get();
        map.put(key, value);
    }

    /**
     * 移除本地线程副本里存储信息的某个值
     *
     * @param key 信息的键
     */
    public synchronized static void remove(String key) {
        Assert.notNull(key, "key值不能为空");
        Map<String, Object> map = get();
        map.put(key, null);
        map.remove(key);
    }

    /**
     * 清空本地线程副本
     */
    public synchronized static void clear() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 获取本地线程副本里的键值对集合
     *
     * @return 本地线程副本里的键值对集合
     */
    private static Map<String, Object> get() {
        Map<String, Object> map = CONTEXT_HOLDER.get();
        if (map == null) {
            map = new WeakHashMap<>();
            CONTEXT_HOLDER.set(map);
        }
        return map;
    }

}
