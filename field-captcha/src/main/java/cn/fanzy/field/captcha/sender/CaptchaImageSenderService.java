package cn.fanzy.field.captcha.sender;

import cn.fanzy.field.captcha.bean.CaptchaCode;
import cn.fanzy.field.captcha.enums.CaptchaType;
import cn.fanzy.field.captcha.enums.ICaptchaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class CaptchaImageSenderService implements CaptchaSenderService {
    public abstract void sendCode(String target, CaptchaCode codeInfo);

    @Override
    public void send(String target, CaptchaCode codeInfo) {
       sendCode(target,codeInfo);
    }

    @Override
    public boolean isSupported(ICaptchaType type) {
        return CaptchaType.IMAGE.equals(type);
    }
}
