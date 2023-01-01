package cn.fanzy.breeze.web.code.model;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 验证码实体类
 *
 * @author fanzaiyang
 * @version 2022-08-16
 */
@Data
public class BreezeCode implements Serializable {
    /**
     * 验证码内容
     */
    private String code;

    private long expireTimeInSeconds;
    /**
     * 验证码的失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 重试次数
     */
    private int retryCount=0;

    /**
     * 最大重试次数
     */
    private int maxRetryCode=1;

    /**
     * 构造函数
     */
    public BreezeCode() {

    }

    /**
     * 构造函数
     *
     * @param expireTimeInSeconds 验证码的有效时间，单位 秒
     * @param code                验证码内容
     * @param maxRetryCode          最大重试计数
     */
    public BreezeCode(String code, int maxRetryCode,long expireTimeInSeconds) {
        this.expireTime = LocalDateTime.now().plusSeconds(expireTimeInSeconds);
        this.code = code;
        this.expireTimeInSeconds=expireTimeInSeconds;
        this.maxRetryCode = maxRetryCode;
    }

    /**
     * 获取验证码是否已经过期
     *
     * @return true表示已过期，false未过期
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
