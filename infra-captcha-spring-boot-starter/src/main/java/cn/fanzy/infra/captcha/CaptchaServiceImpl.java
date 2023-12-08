package cn.fanzy.infra.captcha;

import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaCreatorService;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final Map<String, CaptchaCreatorService<?>> creatorServiceMap;
    private final Map<String, CaptchaSenderService<CaptchaCodeInfo>> senderServiceMap;
    private final CaptchaProperty property;

    @Override
    public CaptchaCodeInfo createAndSend(CaptchaType type, String target) {
        CaptchaCodeInfo codeInfo = switch (type) {
            case IMAGE -> creatorServiceMap.get("imageCaptchaService")
                    .generate(property);
            case MOBILE -> creatorServiceMap.get("mobileCaptchaService")
                    .generate(property);
            case EMAIL -> creatorServiceMap.get("emailCaptchaService")
                    .generate(property);
        };
        switch (type) {
            case IMAGE -> senderServiceMap.get("imageCaptchaService")
                    .send(target,codeInfo);
            case MOBILE -> senderServiceMap.get("mobileCaptchaService")
                    .send(target,codeInfo);
            case EMAIL -> senderServiceMap.get("emailCaptchaService")
                    .send(target,codeInfo);
        };
        return codeInfo;
    }

    @Override
    public boolean verify(CaptchaType type, String target, String code) {
        return false;
    }
}
