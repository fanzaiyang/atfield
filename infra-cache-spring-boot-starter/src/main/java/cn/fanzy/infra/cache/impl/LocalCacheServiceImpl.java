package cn.fanzy.infra.cache.impl;

import cn.fanzy.infra.cache.CacheService;
import cn.fanzy.infra.core.utils.ObjectUtil;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存服务impl
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Slf4j
@RequiredArgsConstructor
public class LocalCacheServiceImpl implements CacheService {
    private static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(0);

    @Override
    public Object get(String key) {
        return get(key, false);
    }

    @Override
    public Object get(String key, boolean isUpdate) {
        return CACHE.get(getKeyName(key), isUpdate);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, false);
    }

    @Override
    public <T> T get(String key, Class<T> clazz, boolean isUpdate) {
        Object object = get(key, isUpdate);
        return ObjectUtil.cast(object, clazz);
    }

    @Override
    public Object pop(String key) {
        Object object = get(key);
        remove(key);
        return object;
    }

    @Override
    public <T> T pop(String key, Class<T> clazz) {
        T obj = get(key, clazz);
        remove(key);
        return obj;
    }


    @Override
    public void put(String key, Object value) {
        CACHE.put(getKeyName(key), value);
    }

    @Override
    public void put(String key, Object value, long expire) {
        CACHE.put(getKeyName(key), value, expire);
    }

    @Override
    public void put(String key, Object value, long expire, TimeUnit timeUnit) {
        CACHE.put(getKeyName(key), value, timeUnit.toMillis(expire));
    }

    @Override
    public void remove(String key) {
        CACHE.remove(getKeyName(key));
    }

    @Override
    public void clear() {
        CACHE.clear();
    }
}
