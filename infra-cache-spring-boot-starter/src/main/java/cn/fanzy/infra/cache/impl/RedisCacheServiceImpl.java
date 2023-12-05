package cn.fanzy.infra.cache.impl;

import cn.fanzy.infra.cache.CacheService;
import cn.fanzy.infra.core.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存服务
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Slf4j
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements CacheService {
    private final RedisTemplate<Object, Object> redisTemplate;


    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(getKeyName(key));
    }

    /**
     * 收到
     *
     * @param key      钥匙
     * @param isUpdate 是否更新，Redis下此参数无效。
     * @return {@link Object}
     */
    @Override
    public Object get(String key, @Deprecated boolean isUpdate) {
        return get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object object = get(key);
        return ObjectUtil.cast(object, clazz);
    }

    /**
     * 收到
     *
     * @param key      钥匙
     * @param clazz    clazz
     * @param isUpdate 是否更新，Redis下此参数无效。
     * @return {@link T}
     */
    @Override
    public <T> T get(String key, Class<T> clazz, @Deprecated boolean isUpdate) {
        return get(key, clazz);
    }

    @Override
    public Object pop(String key) {
        Object object = get(key);
        remove(key);
        return object;
    }

    @Override
    public <T> T pop(String key, Class<T> clazz) {
        T object = get(key, clazz);
        remove(key);
        return object;
    }

    @Override
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(getKeyName(key), value);
    }

    @Override
    public void put(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(getKeyName(key), value, expire);
    }

    @Override
    public void put(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(getKeyName(key), value, expire, timeUnit);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(getKeyName(key));
    }

    @Override
    public void clear() {
        Set<Object> keys = redisTemplate.keys(PREFIX + "*");
        assert keys != null;
        redisTemplate.delete(keys);
    }
}
