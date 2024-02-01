package cn.fanzy.atfield.captcha.sender.impl;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.sender.CaptchaMobileSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认验证码移动发送器服务
 *
 * @author fanzaiyang
 * @date 2024/02/01
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultCaptchaMobileSenderService extends CaptchaMobileSenderService {
    @Override
    public void sendCode(String target, CaptchaCode codeInfo) {
        log.debug("【手机验证码发送器】向手机 {} 发送验证码，验证码的内容为 {} ", target, codeInfo.getCode());
        throw new RuntimeException("请实现手机验证码发送逻辑！Bean->mobileCaptchaSenderService");
    }
}
