package cn.fanzy.infra.captcha.configuration;

import cn.fanzy.infra.captcha.CaptchaService;
import cn.fanzy.infra.captcha.CaptchaServiceImpl;
import cn.fanzy.infra.captcha.creator.CaptchaCreatorService;
import cn.fanzy.infra.captcha.creator.CaptchaEmailCreatorService;
import cn.fanzy.infra.captcha.creator.CaptchaImageCreatorService;
import cn.fanzy.infra.captcha.creator.CaptchaMobileCreatorService;
import cn.fanzy.infra.captcha.creator.impl.DefaultCaptchaEmailCreatorService;
import cn.fanzy.infra.captcha.creator.impl.DefaultCaptchaImageCreatorService;
import cn.fanzy.infra.captcha.creator.impl.DefaultCaptchaMobileCreatorService;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.sender.CaptchaEmailSenderService;
import cn.fanzy.infra.captcha.sender.CaptchaImageSenderService;
import cn.fanzy.infra.captcha.sender.CaptchaMobileSenderService;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import cn.fanzy.infra.captcha.sender.impl.DefaultCaptchaEmailSenderService;
import cn.fanzy.infra.captcha.sender.impl.DefaultCaptchaImageSenderService;
import cn.fanzy.infra.captcha.sender.impl.DefaultCaptchaMobileSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(CaptchaProperty.class)
public class CaptchaCodeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaEmailCreatorService captchaEmailCreatorService() {
        return new DefaultCaptchaEmailCreatorService();
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaImageCreatorService captchaImageCreatorService() {
        return new DefaultCaptchaImageCreatorService();
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaMobileCreatorService captchaMobileCreatorService() {
        return new DefaultCaptchaMobileCreatorService();
    }

    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    @ConditionalOnMissingBean
    public CaptchaEmailSenderService captchaEmailSenderService(JavaMailSender javaMailSender, CaptchaProperty property) {
        return new DefaultCaptchaEmailSenderService(javaMailSender, property);
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaMobileSenderService captchaMobileSenderService() {
        return new DefaultCaptchaMobileSenderService();
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaImageSenderService captchaImageSenderService() {
        return new DefaultCaptchaImageSenderService();
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(List<CaptchaCreatorService> creatorServiceList,
                                         List<CaptchaSenderService> senderServiceList,
                                         CaptchaProperty property) {
        return new CaptchaServiceImpl(creatorServiceList, senderServiceList,property);
    }
}
