/**
 *
 */
package cn.fanzy.breeze.core.cache.service.impl;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.core.storage.LocalScheduledStorage;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 基于内存实现的验证码存储器
 *
 * @author fanzaiyang
 * @since 2022-08-19
 */
@Slf4j
public class BreezeMemoryCacheService implements BreezeCacheService {

    @Override
    public void save(String key, Object value, int expireSecond) {
        log.debug("使用Memory缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        Assert.notNull(value, "存储值不能为空！");
        LocalScheduledStorage.put(key, JSONUtil.toJsonStr(value), expireSecond);
    }

    @Override
    public Object get(String key) {
        log.debug("使用Memory缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        Object object = LocalScheduledStorage.get(key);
        log.info("缓存【{}】值：{}", key, object);
        if (object == null) {
            return object;
        }
        if (JSONUtil.isTypeJSONObject(object.toString())) {
            return JSONUtil.parseObj(object.toString());
        }
        if (JSONUtil.isTypeJSONArray(object.toString())) {
            return JSONUtil.parseArray(object.toString());
        }
        return object;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object obj = get(key);
        if (obj == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(obj), clazz);
    }

    @Override
    public void remove(String key) {
        log.debug("使用Memory缓存！");
        Assert.notBlank(key, "唯一标识不能为空！");
        LocalScheduledStorage.remove(key);
    }
}
