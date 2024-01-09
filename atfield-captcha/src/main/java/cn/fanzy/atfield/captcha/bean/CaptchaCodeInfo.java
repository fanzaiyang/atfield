package cn.fanzy.atfield.captcha.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * captcha代码信息
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaCodeInfo implements CaptchaCode {
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
    @Builder.Default
    private int usedCount = 1;


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public long getExpireSeconds() {
        return expireSeconds;
    }

    @Override
    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    @Override
    public int getUsedCount() {
        return usedCount;
    }

    @Override
    public void setUseCountIncrease() {
        usedCount += 1;
    }
}
