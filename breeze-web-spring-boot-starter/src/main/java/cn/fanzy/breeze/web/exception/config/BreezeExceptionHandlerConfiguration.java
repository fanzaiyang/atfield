package cn.fanzy.breeze.web.exception.config;

import cn.fanzy.breeze.web.exception.handler.BreezeExceptionHandler;
import cn.fanzy.breeze.web.exception.handler.DefaultBreezeExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@AutoConfigureBefore(BreezeWebExceptionConfiguration.class)
public class BreezeExceptionHandlerConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public BreezeExceptionHandler handler() {
        return new DefaultBreezeExceptionHandler();
    }
}
