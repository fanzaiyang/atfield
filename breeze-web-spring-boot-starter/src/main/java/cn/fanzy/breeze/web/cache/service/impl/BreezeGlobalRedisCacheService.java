package cn.fanzy.breeze.web.cache.service.impl;


import ch.qos.logback.core.util.TimeUtil;
import cn.fanzy.breeze.web.cache.service.BreezeGlobalCacheService;
import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;


/**
 * 验证码redis管理器
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@AllArgsConstructor
public class BreezeGlobalRedisCacheService implements BreezeGlobalCacheService {

    /**
     * 简化Redis数据访问代码的Helper类。
     */
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(String key, Object value, int expireSecond) {
        redisTemplate.opsForValue().set(key, value, expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
