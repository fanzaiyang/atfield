package cn.fanzy.atfield.captcha.configuration;

import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import cn.fanzy.atfield.captcha.storage.CaptchaStorageService;
import cn.fanzy.atfield.captcha.storage.impl.LocalCaptchaStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(CaptchaProperty.class)
@AutoConfigureAfter(CaptchaRedisStorageAutoConfiguration.class)
public class CaptchaLocalStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaStorageService captchaStorageService(CaptchaProperty property) {
        return new LocalCaptchaStorageService(property);
    }
    @PostConstruct
    public void checkConfig() {
        log.info("开启 <验证码-Local存储> 相关的配置");
    }
}
