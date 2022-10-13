package cn.fanzy.breeze.web.code.sender.impl;

import cn.fanzy.breeze.web.code.model.BreezeSmsCode;
import cn.fanzy.breeze.web.code.sender.BreezeCodeSender;
import cn.fanzy.breeze.web.code.sender.BreezeSmsSendHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 短信验证码发送器
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@AllArgsConstructor
public class BreezeSmsCodeSender implements BreezeCodeSender<BreezeSmsCode> {

    private final BreezeSmsSendHandler breezeSmsSendHandler;

    @Override
    public void send(ServletWebRequest request, String target, BreezeSmsCode code) {
        log.info("【短信验证码发送器】向手机号 {} 发送短信验证码，验证码的内容为 {} ", target, code.getCode());
        breezeSmsSendHandler.send(target, code.getCode(), request);
    }
}
