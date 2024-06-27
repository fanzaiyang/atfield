package cn.fanzy.atfield.captcha.sender;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;

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
