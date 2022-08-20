/**
 *
 */
package cn.fanzy.breeze.web.code.repository.impl;

import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于内存实现的验证码存储器
 *
 * @author fanzaiyang
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@AllArgsConstructor
public class BreezeSimpleCodeRepository implements BreezeCodeRepository {

    private final BreezeCodeProperties properties;

    private final BreezeCacheService breezeCacheService;
    /**
     * 存储验证码
     *
     * @param key  验证码的唯一标识符
     * @param code 需要存储的验证码
     */
    @Override
    public synchronized void save(String key, BreezeCode code) {
        if(breezeCacheService==null){
            log.warn("未正确开启缓存，无法保存验证码！");
            return;
        }
        breezeCacheService.save(properties.getPrefix()+key, code, (int) code.getExpireTimeInSeconds());
    }

    /**
     * 根据验证码的唯一标识符获取存储的验证码
     *
     * @param key 验证码的唯一标识符
     * @return 存储的验证码
     */
    @Override
    public synchronized BreezeCode get(String key) {
        if(breezeCacheService==null){
            log.warn("未正确开启缓存，无法获取验证码！");
            return null;
        }
        Object obj = breezeCacheService.get(properties.getPrefix()+key);
        Assert.notNull(obj, "验证码不能为空或验证码唯一标识错误！", key);
        return (BreezeCode) obj;
    }

    /**
     * 根据验证码的唯一标识符移除存储的验证码
     *
     * @param key 验证码的唯一标识符
     */
    @Override
    public synchronized void remove(String key) {
        if(breezeCacheService==null){
            log.warn("未正确开启缓存，无法删除验证码！");
            return;
        }
        breezeCacheService.remove(properties.getPrefix()+key);
    }
}
