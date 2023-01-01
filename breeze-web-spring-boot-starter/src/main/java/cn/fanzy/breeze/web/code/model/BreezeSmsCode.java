package cn.fanzy.breeze.web.code.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证码实体类
 *
 * @author fanzaiyang
 * @version 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BreezeSmsCode extends BreezeCode{
    public BreezeSmsCode(String code, int maxRetryCode, long expireTimeInSeconds) {
        super(code, maxRetryCode, expireTimeInSeconds);
    }
}
