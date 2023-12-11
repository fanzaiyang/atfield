package cn.fanzy.infra.captcha.exception;

import cn.fanzy.infra.core.exception.GlobalException;

import java.io.Serial;

/**
 * 没有captcha例外
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
public class CaptchaExpiredException extends GlobalException {
    @Serial
    private static final long serialVersionUID = -291132309394777982L;

    public CaptchaExpiredException(String code, String message) {
        super(code, message);
    }
}
