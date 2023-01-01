package cn.fanzy.breeze.web.code.repository;


import cn.fanzy.breeze.web.code.model.BreezeCode;


/**
 * 验证码存储器
 *
 * @author fanzaiyang
 * @version 2021/09/07
 */
public interface BreezeCodeRepository {

    /**
     * 存储验证码
     *
     * @param key  验证码的唯一标识符
     * @param code 需要存储的验证码
     */
    void save(String key, BreezeCode code);

    /**
     * 根据验证码的唯一标识符获取存储的验证码
     *
     * @param key 验证码的唯一标识符
     * @return 存储的验证码
     */
    BreezeCode get(String key);

    /**
     * 根据验证码的唯一标识符移除存储的验证码
     *
     * @param key 验证码的唯一标识符
     */
    void remove(String key);
}
