package cn.fanzy.infra.captcha.storage;

import cn.fanzy.infra.captcha.bean.CaptchaCode;

/**
 * captcha存储服务
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
public interface CaptchaStorageService {

    /**
     * 保存验证码
     *
     * @param target   目标
     * @param codeInfo 代码信息
     */
    void save(String target, CaptchaCode codeInfo);

    /**
     * 删除验证码
     *
     * @param target 目标
     */
    void delete(String target);

    /**
     * 获取验证码
     *
     * @param target 目标
     * @return 验证码
     */
    Object get(String target);

    /**
     * 收到
     *
     * @param target 目标
     * @param clazz  拍手
     * @return {@link T}
     */
    <T> T get(String target, Class<T> clazz);

    /**
     * 获取并删除验证码
     *
     * @param target 目标
     * @return {@link Object}
     */
    Object pop(String target);

    /**
     * 获取并删除验证码
     *
     * @param target 目标
     * @param clazz  拍手
     * @return {@link T}
     */
    <T> T pop(String target, Class<T> clazz);

    /**
     * 清空所有
     */
    void clear();
}
