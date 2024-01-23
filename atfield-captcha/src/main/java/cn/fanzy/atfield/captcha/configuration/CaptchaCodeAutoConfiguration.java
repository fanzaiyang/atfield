package cn.fanzy.atfield.captcha.configuration;

import cn.fanzy.atfield.captcha.CaptchaService;
import cn.fanzy.atfield.captcha.advice.CaptchaCheckAdvice;
import cn.fanzy.atfield.captcha.creator.impl.DefaultCaptchaEmailCreatorService;
import cn.fanzy.atfield.captcha.creator.impl.DefaultCaptchaImageCreatorService;
import cn.fanzy.atfield.captcha.sender.CaptchaEmailSenderService;
import cn.fanzy.atfield.captcha.sender.CaptchaImageSenderService;
import cn.fanzy.atfield.captcha.sender.CaptchaMobileSenderService;
import cn.fanzy.atfield.captcha.sender.CaptchaSenderService;
import cn.fanzy.atfield.captcha.sender.impl.DefaultCaptchaEmailSenderService;
import cn.fanzy.atfield.captcha.sender.impl.DefaultCaptchaImageSenderService;
import cn.fanzy.atfield.captcha.sender.impl.DefaultCaptchaMobileSenderService;
import cn.fanzy.atfield.captcha.CaptchaServiceImpl;
import cn.fanzy.atfield.captcha.creator.CaptchaCreatorService;
import cn.fanzy.atfield.captcha.creator.CaptchaEmailCreatorService;
import cn.fanzy.atfield.captcha.creator.CaptchaImageCreatorService;
import cn.fanzy.atfield.captcha.creator.CaptchaMobileCreatorService;
import cn.fanzy.atfield.captcha.creator.impl.DefaultCaptchaMobileCreatorService;
import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import cn.fanzy.atfield.captcha.storage.CaptchaStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

/**
 * captcha码自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/12
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@ImportAutoConfiguration({
        CaptchaLocalStorageAutoConfiguration.class,
        CaptchaRedisStorageAutoConfiguration.class,
        CaptchaCheckAdvice.class
})
@EnableConfigurationProperties(CaptchaProperty.class)
public class CaptchaCodeAutoConfiguration {

    private final CaptchaProperty property;

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
    public CaptchaEmailSenderService captchaEmailSenderService(JavaMailSender javaMailSender) {
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
                                         CaptchaStorageService captchaStorageService) {
        return new CaptchaServiceImpl(creatorServiceList, senderServiceList, captchaStorageService, property);
    }
}
