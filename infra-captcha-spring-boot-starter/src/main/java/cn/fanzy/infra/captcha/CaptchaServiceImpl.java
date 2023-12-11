package cn.fanzy.infra.captcha;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaCreatorService;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final List<CaptchaCreatorService> creatorServiceList;
    private final List<CaptchaSenderService> senderServiceList;
    private final CaptchaProperty property;

    @Override
    public CaptchaCode createAndSend(CaptchaType type, String target) {
        Optional<CaptchaCreatorService> creatorService = creatorServiceList.stream()
                .filter(creator -> creator.isSupported(type)).findFirst();
        if (creatorService.isEmpty()) {
            throw new RuntimeException("不支持该类型验证码");
        }
        Optional<CaptchaSenderService> senderService = senderServiceList.stream()
                .filter(sender -> sender.isSupported(type)).findFirst();
        if (senderService.isEmpty()) {
            throw new RuntimeException("不支持该类型验证码");
        }
        CaptchaCode codeInfo = creatorService.get().generate(property);
        senderService.get().send(target, codeInfo);
        return codeInfo;
    }

    @Override
    public boolean verify(CaptchaType type, String target, String code) {
        return false;
    }
}
