package cn.fanzy.infra.captcha.configuration;

import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaCreatorService;
import cn.fanzy.infra.captcha.creator.impl.EmailCaptchaCreatorServiceImpl;
import cn.fanzy.infra.captcha.creator.impl.ImageCaptchaCreatorServiceImpl;
import cn.fanzy.infra.captcha.creator.impl.MobileCaptchaCreatorServiceImpl;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import cn.fanzy.infra.captcha.sender.impl.EmailCaptchaSenderServiceImpl;
import cn.fanzy.infra.captcha.sender.impl.ImageCaptchaSenderServiceImpl;
import cn.fanzy.infra.captcha.sender.impl.MobileCaptchaSenderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(CaptchaProperty.class)
public class CaptchaCodeAutoConfiguration {

    @Bean("emailCaptchaService")
    @ConditionalOnMissingBean(name = "emailCaptchaService")
    public CaptchaCreatorService<CaptchaCodeInfo> emailCaptchaService() {
        return new EmailCaptchaCreatorServiceImpl();
    }

    @Bean("imageCaptchaService")
    @ConditionalOnMissingBean(name = "imageCaptchaService")
    public CaptchaCreatorService<CaptchaImageCodeInfo> imageCaptchaService() {
        return new ImageCaptchaCreatorServiceImpl();
    }

    @Bean("mobileCaptchaService")
    @ConditionalOnMissingBean(name = "mobileCaptchaService")
    public CaptchaCreatorService<CaptchaCodeInfo> mobileCaptchaService() {
        return new MobileCaptchaCreatorServiceImpl();
    }

    @Bean("emailCaptchaSenderService")
    @ConditionalOnMissingBean(name = "emailCaptchaSenderService")
    public CaptchaSenderService<CaptchaCodeInfo> emailCaptchaSenderService(JavaMailSender javaMailSender, CaptchaProperty property) {
        return new EmailCaptchaSenderServiceImpl(javaMailSender, property);
    }

    @Bean("mobileCaptchaSenderService")
    @ConditionalOnMissingBean(name = "mobileCaptchaSenderService")
    public CaptchaSenderService<CaptchaCodeInfo> mobileCaptchaSenderService() {
        return new MobileCaptchaSenderServiceImpl();
    }

    @Bean("imageCaptchaSenderService")
    @ConditionalOnMissingBean(name = "imageCaptchaSenderService")
    public CaptchaSenderService<CaptchaImageCodeInfo> imageCaptchaSenderService() {
        return new ImageCaptchaSenderServiceImpl();
    }
}
