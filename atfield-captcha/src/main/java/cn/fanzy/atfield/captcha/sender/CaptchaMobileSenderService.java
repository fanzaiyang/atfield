package cn.fanzy.atfield.captcha.sender;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.enums.CaptchaType;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public abstract class CaptchaMobileSenderService implements CaptchaSenderService {
    public abstract void sendCode(String target, CaptchaCode codeInfo);

    @Override
    public void send(String target, CaptchaCode codeInfo) {
        sendCode(target, codeInfo);
    }

    @Override
    public boolean isSupported(ICaptchaType type) {
        return CaptchaType.MOBILE.equals(type);
    }
}
