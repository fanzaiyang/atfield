package cn.fanzy.breeze.web.cache.config;

import cn.fanzy.breeze.web.cache.enums.BreezeCacheEnum;
import cn.fanzy.breeze.web.cache.properties.BreezeCacheProperties;
import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.cache.service.impl.BreezeMemoryCacheService;
import cn.fanzy.breeze.web.cache.service.impl.BreezeRedisCacheService;
import cn.fanzy.breeze.web.code.config.BreezeCodeConfiguration;
import cn.fanzy.breeze.web.utils.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE+10)
@Import({BreezeRedisCacheConfiguration.class})
@EnableConfigurationProperties({BreezeCacheProperties.class})
public class BreezeCacheConfiguration {
    private final BreezeCacheProperties properties;

    @Bean
    public BreezeCacheService breezeCacheService() {
        if (BreezeCacheEnum.auto.equals(properties.getType()) ||
                BreezeCacheEnum.redis.equals(properties.getType())) {
            BreezeRedisCacheService bean = SpringUtils.getBean(BreezeRedisCacheService.class);
            if (bean != null) {
                return bean;
            }
        }
        return breezeMemoryCacheService();
    }

    @Bean
    public BreezeMemoryCacheService breezeMemoryCacheService() {
        log.info("「微风组件」开启 <全局缓存Memory> 相关的配置。");
        return new BreezeMemoryCacheService();
    }
}
