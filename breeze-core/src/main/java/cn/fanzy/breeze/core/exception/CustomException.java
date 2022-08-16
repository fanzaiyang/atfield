/**
 *
 */
package cn.fanzy.breeze.core.exception;


/**
 * <p>
 * 自定义异常基类
 * </p>
 * 所有程序中自定义异常的基类
 *
 * @author fanzaiyang
 */
public class CustomException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1825226802361294349L;

    /**
     * 错误码
     */
    protected int code;

    public CustomException() {

    }

    public CustomException(String message) {
        this(0, message);

    }

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
