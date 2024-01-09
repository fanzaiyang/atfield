package cn.fanzy.field.redis.exception;


import cn.fanzy.field.core.exception.GlobalException;

import java.io.Serial;

/**
 * 锁定异常
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
public class LockException extends GlobalException {
    @Serial
    private static final long serialVersionUID = 5700214711842774040L;

    public LockException(String code, String message) {
        super(code, message);
    }
}
