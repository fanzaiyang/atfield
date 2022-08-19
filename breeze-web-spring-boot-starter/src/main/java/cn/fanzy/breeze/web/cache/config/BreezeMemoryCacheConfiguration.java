package cn.fanzy.breeze.web.cache.config;

import cn.fanzy.breeze.web.cache.properties.BreezeCacheProperties;
import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.cache.service.impl.BreezeMemoryCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 基于Redis的验证码存储器自动配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Configuration
@AutoConfigureAfter(BreezeRedisCacheConfiguration.class)
@EnableConfigurationProperties({BreezeCacheProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.cache", name = {"type"}, havingValue = "memory",matchIfMissing = true)
public class BreezeMemoryCacheConfiguration {

    @Bean
    public BreezeCacheService breezeCacheService() {
        log.info("「微风组件」开启 <全局缓存Memory> 相关的配置。");
        return new BreezeMemoryCacheService();
    }
}
