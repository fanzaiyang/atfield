package cn.fanzy.field.captcha.exception;


import cn.fanzy.field.core.exception.GlobalException;

import java.io.Serial;

/**
 * captcha无效异常
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
public class CaptchaInvalidException extends GlobalException {
    @Serial
    private static final long serialVersionUID = -291132309394777982L;

    public CaptchaInvalidException(String code, String message) {
        super(code, message);
    }
}
