package cn.fanzy.infra.captcha.sender;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;

/**
 * 创建验证码的服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface CaptchaSenderService {

    void send(String target, CaptchaCode codeInfo);
    boolean isSupported(CaptchaType type);
}
