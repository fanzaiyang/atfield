package cn.fanzy.breeze.web.code.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
