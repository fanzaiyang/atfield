package cn.fanzy.atfield.core.exception;


import cn.hutool.core.util.StrUtil;
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

    /**
     * 全局异常
     *
     * @param message 消息
     * @param params  参数
     */
    public GlobalException(String message, Object... params) {
        this("-1", StrUtil.format(message, params));
    }

    public GlobalException(String code, String message) {
        super(message);
        this.code = code;
    }
}
