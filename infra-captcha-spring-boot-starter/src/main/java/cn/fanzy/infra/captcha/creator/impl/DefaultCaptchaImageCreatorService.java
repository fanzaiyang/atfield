package cn.fanzy.infra.captcha.creator.impl;

import cn.fanzy.infra.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaImageCreatorService;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.img.ImgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 默认电子邮件captcha创建者服务
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultCaptchaImageCreatorService extends CaptchaImageCreatorService {
    @Override
    public CaptchaImageCodeInfo generateCode(CaptchaProperty captchaProperty) {
        CaptchaProperty.Image property = captchaProperty.getImage();
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(property.getWidth(),
                property.getHeight(), property.getLength(), 2);

        String code = getRandomCode(property.getLength(), property.isContainsLetter(), property.isContainsNumber());
        Image image = captcha.createImage(code);
        return CaptchaImageCodeInfo.builder()
                .code(code)
                .expireSeconds(property.getExpireSeconds())
                .maxRetryCount(property.getRetryCount())
                .usedCount(0)
                .image(ImgUtil.copyImage(image, BufferedImage.TYPE_INT_RGB))
                .imageBase64(ImgUtil.toBase64DataUri(image, ImgUtil.IMAGE_TYPE_PNG))
                .build();
    }
}
