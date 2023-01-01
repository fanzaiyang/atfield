package cn.fanzy.breeze.web.safe.config;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.logs.config.BreezeLogsConfiguration;
import cn.fanzy.breeze.web.safe.aop.BreezeSafeAop;
import cn.fanzy.breeze.web.safe.properties.BreezeSafeProperties;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import cn.fanzy.breeze.web.safe.service.BreezeSafeServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
@AutoConfigureBefore({BreezeLogsConfiguration.class, BreezeSafeAop.class})
@ImportAutoConfiguration(BreezeSafeAop.class)
@EnableConfigurationProperties(BreezeSafeProperties.class)
public class BreezeSafeConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeSafeService breezeSafeService(BreezeCacheService cacheService, BreezeSafeProperties properties) {
        return new BreezeSafeServiceImpl(cacheService, properties);
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启<全局安全>相关的配置。");
    }

}
