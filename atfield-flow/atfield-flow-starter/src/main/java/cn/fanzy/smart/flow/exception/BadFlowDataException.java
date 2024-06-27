package cn.fanzy.smart.flow.exception;

import cn.fanzy.atfield.core.exception.GlobalException;

import java.io.Serial;

/**
 * 错误流数据异常
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
public class BadFlowDataException extends GlobalException {
    @Serial
    private static final long serialVersionUID = 4554352298063496082L;

    public BadFlowDataException(String message) {
        this("-1", message);
    }

    public BadFlowDataException(String code, String message) {
        super(code, message);
    }

}
