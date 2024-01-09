package cn.fanzy.field.captcha.configuration;

import cn.fanzy.field.captcha.property.CaptchaProperty;
import cn.fanzy.field.captcha.storage.CaptchaStorageService;
import cn.fanzy.field.captcha.storage.impl.RedisCaptchaStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(CaptchaProperty.class)
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore(CaptchaLocalStorageAutoConfiguration.class)
public class CaptchaRedisStorageAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CaptchaStorageService captchaStorageService(RedisTemplate<Object, Object> redisTemplate, CaptchaProperty property) {
        return new RedisCaptchaStorageService(redisTemplate, property);
    }
    @PostConstruct
    public void checkConfig() {
        log.info("开启 <验证码-redis存储> 相关的配置");
    }
}
