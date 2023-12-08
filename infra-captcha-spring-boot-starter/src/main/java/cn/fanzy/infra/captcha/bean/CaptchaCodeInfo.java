package cn.fanzy.infra.captcha.bean;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * captcha代码信息
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaCodeInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1818762193843128112L;

    /**
     * 验证码值
     */
    private String code;

    /**
     * 过期秒
     */
    private long expireSeconds;


    /**
     * 最大重试次数
     */
    private int maxRetryCount;

    /**
     * 已重试次数
     */
    private int usedCount;

    public LocalDateTime getExpireAt() {
        return LocalDateTime.now().plusSeconds(expireSeconds);
    }

    public boolean isExpired() {
        if (LocalDateTime.now().isAfter(getExpireAt())) {
            return true;
        }
        return usedCount > maxRetryCount;
    }
}
