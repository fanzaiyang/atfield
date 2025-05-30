package cn.fanzy.atfield.captcha.storage.impl;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import cn.fanzy.atfield.captcha.storage.CaptchaStorageService;
import cn.fanzy.atfield.core.utils.ObjectUtils;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本地captcha存储服务
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Slf4j
@RequiredArgsConstructor
public class LocalCaptchaStorageService implements CaptchaStorageService {
    private static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(0);

    private final CaptchaProperty property;

    @Override
    public void save(String target, CaptchaCode codeInfo) {
        if (codeInfo.getExpireSeconds() == -1 || codeInfo.getExpireSeconds() == 0) {
            CACHE.put(property.getPrefix() + target, codeInfo);
        } else {
            CACHE.put(property.getPrefix() + target, codeInfo, codeInfo.getExpireSeconds() * 1000);
        }

    }

    @Override
    public void delete(String target) {
        CACHE.remove(property.getPrefix() + target);
    }

    @Override
    public Object get(String target) {
        return CACHE.get(property.getPrefix() + target);
    }

    @Override
    public <T> T get(String target, Class<T> clazz) {
        return ObjectUtils.cast(get(target), clazz);
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
        return ObjectUtils.cast(object, clazz);
    }

    @Override
    public void clear() {
        CACHE.clear();
    }
}
