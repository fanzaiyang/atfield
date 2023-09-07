package cn.fanzy.breeze.web.redis.lock.exception;

/**
 * 防止重复提交异常
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
public class PreventDuplicateSubmitException extends LockErrorException{
    private static final long serialVersionUID = -5088903115052147237L;

    public PreventDuplicateSubmitException() {

    }

    public PreventDuplicateSubmitException(String message) {
        this(400, message);

    }

    public PreventDuplicateSubmitException(int code, String message) {
        super(message);
        this.code = code;
    }

    public PreventDuplicateSubmitException(Throwable cause) {
        super(cause);
    }

    public PreventDuplicateSubmitException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreventDuplicateSubmitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
