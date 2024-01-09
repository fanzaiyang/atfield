package cn.fanzy.field.captcha.sender;

import cn.fanzy.field.captcha.bean.CaptchaCode;
import cn.fanzy.field.captcha.enums.ICaptchaType;

/**
 * 创建验证码的服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface CaptchaSenderService {

    void send(String target, CaptchaCode codeInfo);

    /**
     * 支持
     *
     * @param type 类型
     * @return boolean
     */
    boolean isSupported(ICaptchaType type);
}
