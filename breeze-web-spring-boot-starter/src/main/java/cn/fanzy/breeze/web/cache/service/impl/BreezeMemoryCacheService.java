/**
 *
 */
package cn.fanzy.breeze.web.cache.service.impl;

import cn.fanzy.breeze.core.storage.LocalScheduledStorage;
import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.hutool.core.lang.Assert;


/**
 * 基于内存实现的验证码存储器
 *
 * @author fanzaiyang
 * @date 2022-08-19
 */
public class BreezeMemoryCacheService implements BreezeCacheService {

    @Override
    public void save(String key, Object value, int expireSecond) {
        Assert.notBlank(key, "唯一标识不能为空！");
        Assert.notNull(value, "存储值不能为空！");
        LocalScheduledStorage.put(key, value, expireSecond);
    }

    @Override
    public Object get(String key) {
        Assert.notBlank(key, "唯一标识不能为空！");
        return LocalScheduledStorage.get(key);
    }

    @Override
    public void remove(String key) {
        Assert.notBlank(key, "唯一标识不能为空！");
        LocalScheduledStorage.remove(key);
    }
}
