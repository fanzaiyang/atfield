package cn.fanzy.field.captcha.sender.impl;

import cn.fanzy.field.captcha.bean.CaptchaCode;
import cn.fanzy.field.captcha.sender.CaptchaMobileSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultCaptchaMobileSenderService extends CaptchaMobileSenderService {
    @Override
    public void sendCode(String target, CaptchaCode codeInfo) {
        log.debug("【手机验证码发送器】向手机 {} 发送验证码，验证码的内容为 {} ", target, codeInfo.getCode());
        throw new RuntimeException("请实现手机验证码发送逻辑！Bean->mobileCaptchaSenderService");
    }
}
