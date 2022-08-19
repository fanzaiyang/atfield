package cn.fanzy.breeze.web.code.generator.impl;

import cn.fanzy.breeze.web.code.generator.BreezeCodeGenerator;
import cn.fanzy.breeze.web.code.model.BreezeImageCode;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.utils.HttpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.img.ImgUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
@AllArgsConstructor
public class BreezeImageCodeGenerator implements BreezeCodeGenerator<BreezeImageCode> {
    @Override
    public BreezeImageCode generate(ServletWebRequest servletWebRequest, BreezeCodeProperties properties) {
        BreezeCodeProperties.ImageCodeProperties image = properties.getImage();
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(image.getWidth(), image.getHeight(), image.getLength(), 2);
        String code = RandomStringUtils.random(image.getLength(), image.getContainLetter(), image.getContainNumber());
        Image captchaImage = lineCaptcha.createImage(code);
        BreezeImageCode imageCode = new BreezeImageCode(image.getExpireIn(), code, image.getRetryCount() == null ? properties.getRetryCount() : image.getRetryCount());
        imageCode.setImage(ImgUtil.copyImage(captchaImage,BufferedImage.TYPE_INT_RGB));
        imageCode.setImageBase64(ImgUtil.toBase64DataUri(captchaImage,ImgUtil.IMAGE_TYPE_PNG));
        imageCode.setCode(code);
        return imageCode;
    }

    @Override
    public String generateKey(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.ImageCodeProperties image = properties.getImage();
        return HttpUtil.extract(request,image.getCodeKey())+"";
    }

    @Override
    public String getCodeInRequest(ServletWebRequest request, BreezeCodeProperties properties) {
        BreezeCodeProperties.ImageCodeProperties image = properties.getImage();
        return HttpUtil.extract(request,image.getCodeValue())+"";
    }

}
