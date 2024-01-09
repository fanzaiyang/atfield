package cn.fanzy.field.captcha.bean;

import cn.fanzy.field.captcha.exception.CaptchaExpiredException;
import cn.fanzy.field.captcha.exception.CaptchaInvalidException;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
public interface CaptchaCode extends Serializable {

    /**
     * 获取验证码
     *
     * @return {@link String}
     */
    String getCode();

    /**
     * 获取过期秒数
     *
     * @return long
     */
    long getExpireSeconds();

    /**
     * 获取最大重试次数
     *
     * @return int
     */
    int getMaxRetryCount();

    /**
     * 使用计数
     *
     * @return int
     */
    int getUsedCount();

    /**
     * 设置使用计数
     */
    void setUseCountIncrease();

    default LocalDateTime getExpireAt() {
        return LocalDateTime.now().plusSeconds(getExpireSeconds());
    }

    /**
     * 是否已过期
     *
     * @return boolean
     */
    default boolean isExpired() {
        return LocalDateTime.now().isAfter(getExpireAt());
    }

    /**
     * 是否超过重拾次数
     *
     * @return boolean
     */
    default boolean isOverCount() {
        return getUsedCount() > getMaxRetryCount();
    }

    /**
     * 是否无效
     *
     * @return boolean
     */
    default boolean isInvalid() {
        return isExpired() || isOverCount();
    }

    /**
     * 预先验证
     */
    default void preVerify() {
        if (isExpired()) {
            throw new CaptchaExpiredException("5001", "验证码已过期，请重新获取！");
        }
        if (isOverCount()) {
            throw new CaptchaInvalidException("5002", "验证码已过期，请重新获取!");
        }
    }
}
