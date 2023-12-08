package cn.fanzy.infra.ip.exception;

import cn.fanzy.infra.core.exception.GlobalException;

import java.io.Serial;

/**
 * ip无效异常
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public class IpInvalidException extends GlobalException {
    @Serial
    private static final long serialVersionUID = -9034452926501845388L;

    public IpInvalidException(String code, String message) {
        super(code, message);
    }
}
