package cn.fanzy.infra.captcha.creator.impl;

import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaMobileCreatorService;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认电子邮件captcha创建者服务
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultCaptchaMobileCreatorService extends CaptchaMobileCreatorService {

    @Override
    public CaptchaCodeInfo generateCode(CaptchaProperty captchaProperty) {
        CaptchaProperty.Mobile property = captchaProperty.getMobile();
        String code = getRandomCode(property.getLength(), property.isContainLetter(), property.isContainNumber());
        return CaptchaCodeInfo.builder()
                .code(code)
                .expireSeconds(
                        property.getExpireSeconds() == null ?
                                captchaProperty.getExpireSeconds() :
                                property.getExpireSeconds()
                )
                .maxRetryCount(property.getRetryCount())
                .usedCount(0)
                .build();
    }
}
