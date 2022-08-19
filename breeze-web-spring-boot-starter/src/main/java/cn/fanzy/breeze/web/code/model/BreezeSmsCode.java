package cn.fanzy.breeze.web.code.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证码实体类
 *
 * @author fanzaiyang
 * @date 2022-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BreezeSmsCode extends BreezeCode{
    public BreezeSmsCode(long expireTimeInSeconds, String code, int retryCount) {
        super(expireTimeInSeconds, code, retryCount);
    }
}
