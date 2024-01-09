package cn.fanzy.atfield.core.exception;


import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 全局异常
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Getter
@Setter
public class GlobalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8316250187148605835L;

    /**
     * 错误码
     */
    private String code;

    public GlobalException() {
    }

    public GlobalException(String message) {
        this("-1", message);
    }

    public GlobalException(String code, String message) {
        super(message);
        this.code = code;
    }
}
