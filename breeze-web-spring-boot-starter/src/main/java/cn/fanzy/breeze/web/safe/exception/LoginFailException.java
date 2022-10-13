package cn.fanzy.breeze.web.safe.exception;

public class LoginFailException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1825226802361294349L;

    /**
     * 错误码
     */

    public LoginFailException() {

    }

    public LoginFailException(String message) {
        super(message);

    }

    public LoginFailException(Throwable cause) {
        super(cause);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
