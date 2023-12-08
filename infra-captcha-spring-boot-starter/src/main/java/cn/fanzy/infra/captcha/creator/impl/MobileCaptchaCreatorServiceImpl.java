package cn.fanzy.infra.captcha.creator.impl;

import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaCreatorService;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 移动创建者服务impl
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Slf4j
@RequiredArgsConstructor
public class MobileCaptchaCreatorServiceImpl implements CaptchaCreatorService<CaptchaCodeInfo> {
    @Override
    public CaptchaCodeInfo generate(CaptchaProperty captchaProperty) {
        CaptchaProperty.Mobile property = captchaProperty.getMobile();
        String code = getRandomCode(property.getLength(), property.isContainLetter(), property.isContainNumber());
        return CaptchaCodeInfo.builder()
                .code(code)
                .expireSeconds(property.getExpireSeconds())
                .maxRetryCount(property.getRetryCount())
                .usedCount(0)
                .build();
    }
}
