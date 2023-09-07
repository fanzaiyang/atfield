package cn.fanzy.breeze.web.redis.lock.exception;

/**
 * 锁定错误异常
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
public class LockErrorException extends RuntimeException{
    private static final long serialVersionUID = -5088903115052147237L;

    /**
     * 错误码
     */
    protected int code;

    public LockErrorException() {

    }

    public LockErrorException(String message) {
        this(400, message);

    }

    public LockErrorException(int code, String message) {
        super(message);
        this.code = code;
    }

    public LockErrorException(Throwable cause) {
        super(cause);
    }

    public LockErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
