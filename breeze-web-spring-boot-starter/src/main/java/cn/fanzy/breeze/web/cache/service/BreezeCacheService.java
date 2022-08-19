package cn.fanzy.breeze.web.cache.service;

/**
 * 微风全局缓存
 * 若开启了redis则保存到redis否则存在内存
 *
 * @author fanzaiyang
 * @date 2022-08-19
 */
public interface BreezeCacheService {

    /**
     * 保存
     * 存储
     *
     * @param key          唯一标识符
     * @param value        需要存储的内容
     * @param expireSecond 过期时间，单位：秒，大于0
     */
    default void save(String key, Object value, int expireSecond){};

    /**
     * 根据唯一标识符获取存储的内容
     *
     * @param key 唯一标识符
     * @return 存储的内容
     */
    default Object get(String key){return null;};

    /**
     * 根据唯一标识符移除存储的内容
     *
     * @param key 唯一标识符
     */
    default void remove(String key){};


}
