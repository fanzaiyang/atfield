package cn.fanzy.atfield.satoken.configuration;

import cn.fanzy.atfield.cache.CacheService;
import cn.fanzy.atfield.captcha.CaptchaService;
import cn.fanzy.atfield.satoken.login.advice.LoginAdvice;
import cn.fanzy.atfield.satoken.login.property.LoginProperty;
import cn.fanzy.atfield.satoken.login.service.LoginAdviceService;
import cn.fanzy.atfield.satoken.login.service.LoginCaptchaService;
import cn.fanzy.atfield.satoken.login.service.impl.LoginAdviceServiceImpl;
import cn.fanzy.atfield.satoken.login.service.impl.LoginCaptchaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 登录额外配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@RequiredArgsConstructor
@Configuration
@ImportAutoConfiguration({LoginAdvice.class})
@EnableConfigurationProperties(LoginProperty.class)
public class LoginExtraConfiguration {
    private final LoginProperty property;

    @Bean
    @ConditionalOnMissingBean
    public LoginCaptchaService loginCaptchaService(CaptchaService captchaService) {
        return new LoginCaptchaServiceImpl(captchaService, property);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginAdviceService loginAdviceService(CacheService cacheService, LoginCaptchaService loginCaptchaService) {
        return new LoginAdviceServiceImpl(cacheService, loginCaptchaService);
    }
}
