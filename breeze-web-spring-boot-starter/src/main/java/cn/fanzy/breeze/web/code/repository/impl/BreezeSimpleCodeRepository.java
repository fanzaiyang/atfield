/**
 *
 */
package cn.fanzy.breeze.web.code.repository.impl;

import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存实现的验证码存储器
 *
 * @author fanzaiyang
 * @version 1.0.0
 * @since 1.0.0
 */
@AllArgsConstructor
public class BreezeSimpleCodeRepository implements BreezeCodeRepository {

    private final BreezeCacheService breezeCacheService;

    /**
     * 存储验证码
     *
     * @param key  验证码的唯一标识符
     * @param code 需要存储的验证码
     */
    @Override
    public synchronized void save(String key, BreezeCode code) {
        breezeCacheService.save(key, code, code.getExpireTimeInSeconds());
    }

    /**
     * 根据验证码的唯一标识符获取存储的验证码
     *
     * @param key 验证码的唯一标识符
     * @return 存储的验证码
     */
    @Override
    public synchronized BreezeCode get(String key) {
        Object obj = breezeCacheService.get(key);
        Assert.notNull(obj, "未找到key为「{}」的数据！", key);
        return (BreezeCode) obj;
    }

    /**
     * 根据验证码的唯一标识符移除存储的验证码
     *
     * @param key 验证码的唯一标识符
     */
    @Override
    public synchronized void remove(String key) {
        breezeCacheService.remove(key);
    }
}
