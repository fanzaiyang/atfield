package cn.fanzy.breeze.core.cache.config;


import cn.fanzy.breeze.core.cache.properties.BreezeCacheProperties;
import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.core.cache.service.impl.BreezeMemoryCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(BreezeRedisCacheConfiguration.class)
@EnableConfigurationProperties({BreezeCacheProperties.class})
@ImportAutoConfiguration(BreezeRedisCacheConfiguration.class)
public class BreezeCacheConfiguration {
    private final BreezeCacheProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public BreezeCacheService breezeCacheService() {
        log.info("「微风组件」开启 <全局缓存Memory> 相关的配置。");
        return new BreezeMemoryCacheService();
    }


}
