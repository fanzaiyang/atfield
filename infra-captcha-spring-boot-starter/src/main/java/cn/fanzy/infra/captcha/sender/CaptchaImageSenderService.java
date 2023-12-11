package cn.fanzy.infra.captcha.sender;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;
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
    public boolean isSupported(CaptchaType type) {
        return CaptchaType.IMAGE.equals(type);
    }
}
