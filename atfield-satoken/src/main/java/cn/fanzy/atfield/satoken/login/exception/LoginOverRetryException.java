package cn.fanzy.atfield.satoken.login.exception;

import cn.fanzy.atfield.core.exception.GlobalException;

import java.io.Serial;

/**
 * 重试登录异常
 *
 * @author fanzaiyang
 * @date 2024/01/16
 */
public class LoginOverRetryException extends GlobalException {
    @Serial
    private static final long serialVersionUID = -5686055647674402230L;

    public LoginOverRetryException() {
    }

    public LoginOverRetryException(String message) {
        this("-1", message);
    }

    public LoginOverRetryException(String code, String message) {
        super(code, message);
    }
}
