package cn.fanzy.breeze.core.cache.config;


import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.core.cache.service.impl.BreezeMemoryCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 微风缓存配置
 *
 * @author fanzaiyang
 * @date 2023/09/12
 */
@Slf4j
@Configuration
public class BreezeMemoryCacheConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BreezeCacheService breezeCacheService() {
        log.info("「微风组件」开启 <全局缓存Memory> 相关的配置。");
        return new BreezeMemoryCacheService();
    }

    @PostConstruct
    public void init() {
        log.info("init「微风组件」初始化 <全局缓存Memory> 相关的配置。");
    }

}
