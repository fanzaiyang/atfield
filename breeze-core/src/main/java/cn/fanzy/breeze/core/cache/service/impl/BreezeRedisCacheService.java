package cn.fanzy.breeze.core.cache.service.impl;


import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * 验证码redis管理器
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
public class BreezeRedisCacheService implements BreezeCacheService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void save(String key, Object value, int expireSecond) {
        log.debug("使用Redis缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        Assert.notNull(value, "存储值不能为空！");
        redisTemplate.opsForValue().set(key, value, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        log.debug("使用Redis缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        log.debug("使用Redis缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        redisTemplate.delete(key);
    }
}
