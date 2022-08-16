/**
 *
 */
package cn.fanzy.breeze.web.code.repository.impl;

import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;

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
public class BreezeSimpleCodeRepository implements BreezeCodeRepository {

    private final static Map<String, BreezeCode> MAP = new ConcurrentHashMap<>();

    /**
     * 存储验证码
     *
     * @param key  验证码的唯一标识符
     * @param code 需要存储的验证码
     */
    @Override
    public synchronized void save(String key, BreezeCode code) {
        MAP.put(key, code);

    }

    /**
     * 根据验证码的唯一标识符获取存储的验证码
     *
     * @param key 验证码的唯一标识符
     * @return 存储的验证码
     */
    @Override
    public synchronized BreezeCode get(String key) {
        BreezeCode code = MAP.get(key);
        return code;
    }

    /**
     * 根据验证码的唯一标识符移除存储的验证码
     *
     * @param key 验证码的唯一标识符
     */
    @Override
    public synchronized void remove(String key) {
        Iterator<String> it = MAP.keySet().iterator();
        synchronized (BreezeSimpleCodeRepository.class) {
            while (it.hasNext()) {
                String currentKey = it.next();
                MAP.keySet().remove(currentKey);
            }

        }

    }

}
