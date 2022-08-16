package cn.fanzy.breeze.web.code.repository.impl;


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
public class BreezeRedisCodeRepository implements BreezeCodeRepository {

    /**
     * 简化Redis数据访问代码的Helper类。
     */
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 验证码属性配置
     */
    private BreezeCodeProperties codeProperties;

    /**
     * 存储验证码
     *
     * @param key  验证码的唯一标识符
     * @param code 需要存储的验证码
     */
    @Override
    public void save(String key, BreezeCode code) {

        // 验证码剩余的有效期
        long expireSecond = LocalDateTime.now().until(code.getExpireTime(), ChronoUnit.SECONDS);
        if (expireSecond > 0) {
            redisTemplate.opsForValue().set(this.getKey(key), code, expireSecond, TimeUnit.SECONDS);
        }

    }

    /**
     * 根据验证码的唯一标识符获取存储的验证码
     *
     * @param key 验证码的唯一标识符
     * @return 存储的验证码
     */
    @Override
    public BreezeCode get(String key) {

        return (BreezeCode) redisTemplate.opsForValue().get(this.getKey(key));
    }

    /**
     * 根据验证码的唯一标识符移除存储的验证码
     *
     * @param key 验证码的唯一标识符
     */
    @Override
    public void remove(String key) {
        redisTemplate.delete(this.getKey(key));

    }

    /**
     * 生成key值
     *
     * @param key
     * @return
     */

    /**
     * 获取操作Redis时的键
     *
     * @param key 验证码的唯一标识符
     * @return 操作Redis时的键
     */
    private String getKey(String key) {
        return this.codeProperties.getPrefix() + ":" + key;
    }
}
