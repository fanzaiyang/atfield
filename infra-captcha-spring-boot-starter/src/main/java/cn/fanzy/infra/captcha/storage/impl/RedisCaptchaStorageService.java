package cn.fanzy.infra.captcha.storage.impl;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.storage.CaptchaStorageService;
import cn.fanzy.infra.core.utils.ObjectUtil;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * rediscaptcha存储服务
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Slf4j
@RequiredArgsConstructor
public class RedisCaptchaStorageService implements CaptchaStorageService {
    private final RedisTemplate<Object, Object> redisTemplate;

    private final CaptchaProperty property;

    @Override
    public void save(String target, CaptchaCode codeInfo) {
        if (codeInfo.getExpireSeconds() == -1 || codeInfo.getExpireSeconds() == 0) {
            redisTemplate.opsForValue().set(property.getPrefix() + target, codeInfo);
        } else {
            redisTemplate.opsForValue().set(property.getPrefix() + target, codeInfo, codeInfo.getExpireSeconds(),
                    TimeUnit.SECONDS);
        }

    }

    @Override
    public void delete(String target) {
        redisTemplate.delete(property.getPrefix() + target);
    }

    @Override
    public Object get(String target) {
        return redisTemplate.opsForValue().get(property.getPrefix() + target);
    }

    @Override
    public <T> T get(String target, Class<T> clazz) {
        return ObjectUtil.cast(get(target), clazz);
    }

    @Override
    public Object pop(String target) {
        Object object = get(target);
        delete(target);
        return object;
    }

    @Override
    public <T> T pop(String target, Class<T> clazz) {
        Object object = get(target);
        delete(target);
        return ObjectUtil.cast(object, clazz);
    }

    @Override
    public void clear() {
        Set<Object> keys = redisTemplate.keys(property.getPrefix() + "*");
        if (CollUtil.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }
}
