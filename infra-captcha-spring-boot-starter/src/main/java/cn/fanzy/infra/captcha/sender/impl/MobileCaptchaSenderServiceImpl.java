package cn.fanzy.infra.captcha.sender.impl;

import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class MobileCaptchaSenderServiceImpl implements CaptchaSenderService<CaptchaCodeInfo> {
    @Override
    public void send(String target, CaptchaCodeInfo codeInfo) {
        log.debug("【手机验证码发送器】向手机 {} 发送验证码，验证码的内容为 {} ", target, codeInfo.getCode());
        throw new RuntimeException("请实现手机验证码发送逻辑！Bean->mobileCaptchaSenderService");
    }
}
