/**
 *
 */
package cn.fanzy.breeze.core.cache.service.impl;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.core.storage.LocalScheduledStorage;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;


/**
 * 基于内存实现的验证码存储器
 *
 * @author fanzaiyang
 * @version 2022-08-19
 */
@Slf4j
public class BreezeMemoryCacheService implements BreezeCacheService {

    @Override
    public void save(String key, Object value, int expireSecond) {
        log.debug("使用Memory缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        Assert.notNull(value, "存储值不能为空！");
        LocalScheduledStorage.put(key, value, expireSecond);
    }

    @Override
    public Object get(String key) {
        log.debug("使用Memory缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        return LocalScheduledStorage.get(key);
    }

    @Override
    public void remove(String key) {
        log.debug("使用Memory缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        LocalScheduledStorage.remove(key);
    }
}
