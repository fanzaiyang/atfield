package cn.fanzy.infra.redis.exception;

import cn.fanzy.infra.core.exception.GlobalException;

import java.io.Serial;

/**
 * 锁定异常
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
public class LockFormException extends GlobalException {
    @Serial
    private static final long serialVersionUID = 5700214711842774040L;

    public LockFormException(String code, String message) {
        super(code, message);
    }
}
