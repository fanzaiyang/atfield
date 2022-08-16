package cn.fanzy.breeze.web.code.model;

import lombok.Data;

/**
 * 验证码实体类
 *
 * @author fanzaiyang
 * @date 2022-08-16
 */
@Data
public class BreezeEmailCode extends BreezeCode{
    public BreezeEmailCode(long expireTimeInSeconds, String code, int retryCount) {
        super(expireTimeInSeconds, code, retryCount);
    }
}
