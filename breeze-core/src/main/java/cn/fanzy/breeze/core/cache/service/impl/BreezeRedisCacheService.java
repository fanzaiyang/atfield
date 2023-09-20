package cn.fanzy.breeze.core.cache.service.impl;


import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * 验证码redis管理器
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
@RequiredArgsConstructor
public class BreezeRedisCacheService implements BreezeCacheService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(String key, Object value, int expireSecond) {
        log.debug("使用Redis缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        Assert.notNull(value, "存储值不能为空！");
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), expireSecond, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        log.debug("使用Redis缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        String str = stringRedisTemplate.opsForValue().get(key);
        log.info("缓存【{}】值：{}", key, str);
        if (JSONUtil.isTypeJSONObject(str)) {
            return JSONUtil.parseObj(str);
        }
        if (JSONUtil.isTypeJSONArray(str)) {
            return JSONUtil.parseArray(str);
        }
        return str;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Assert.notBlank(key, "唯一标识不能为空！");
        String str = stringRedisTemplate.opsForValue().get(key);
        log.info("缓存【{}】值：{}", key, str);
        if (StrUtil.isBlank(str)) {
            return null;
        }
        return JSONUtil.toBean(str, clazz);
    }

    @Override
    public void remove(String key) {
        log.debug("使用Redis缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        stringRedisTemplate.delete(key);
    }
}
