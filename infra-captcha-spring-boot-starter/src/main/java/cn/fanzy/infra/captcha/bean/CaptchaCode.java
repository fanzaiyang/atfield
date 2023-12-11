package cn.fanzy.infra.captcha.bean;

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

    int getUsedCount();

    default LocalDateTime getExpireAt() {
        return LocalDateTime.now().plusSeconds(getExpireSeconds());
    }

    /**
     * 是否已过期
     *
     * @return boolean
     */
    default boolean isExpired() {
        if (LocalDateTime.now().isAfter(getExpireAt())) {
            return true;
        }
        return getUsedCount() > getMaxRetryCount();
    }
}
