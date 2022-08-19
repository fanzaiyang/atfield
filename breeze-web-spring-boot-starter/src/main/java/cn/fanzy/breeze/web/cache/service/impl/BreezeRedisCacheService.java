package cn.fanzy.breeze.web.cache.service.impl;


import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * 验证码redis管理器
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@AllArgsConstructor
public class BreezeRedisCacheService implements BreezeCacheService {

    /**
     * 简化Redis数据访问代码的Helper类。
     */
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String key, Object value, int expireSecond) {
        Assert.notBlank(key, "唯一标识不能为空！");
        Assert.notNull(value, "存储值不能为空！");
        redisTemplate.opsForValue().set(key, value, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        Assert.notBlank(key, "唯一标识不能为空！");
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        Assert.notBlank(key, "唯一标识不能为空！");
        redisTemplate.delete(key);
    }
}
