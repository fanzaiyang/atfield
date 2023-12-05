package cn.fanzy.infra.cache.configuration;

import cn.fanzy.infra.cache.CacheService;
import cn.fanzy.infra.cache.property.CacheProperty;
import cn.fanzy.infra.core.exception.GlobalException;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 本地缓存配置
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@ImportAutoConfiguration({LocalCacheAutoConfiguration.class, RedisCacheAutoConfiguration.class})
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableCaching
@EnableConfigurationProperties(CacheProperty.class)
public class CacheAutoConfiguration {
    private final CacheProperty property;

    @Bean(name = "cacheService")
    @ConditionalOnMissingBean(name = "cacheService")
    public CacheService cacheService() {
        boolean hasRedis = SpringUtil.getApplicationContext()
                .containsBean("redisCacheService");
        switch (property.getType()) {
            case AUTO:
                if (hasRedis) {
                    return getRedisCacheService();
                } else {
                    return getLocalCacheService();
                }
            case LOCAL:
                return getLocalCacheService();
            case REDIS:
                return getRedisCacheService();
            default:
                throw new GlobalException("未知的缓存类型");
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    private CacheService getLocalCacheService() {
        log.info("开启 <全局缓存-内存> 相关的配置。");
        return SpringUtil.getBean("localCacheService", CacheService.class);
    }

    private CacheService getRedisCacheService() {
        log.info("开启 <全局缓存-Redis> 相关的配置。");
        return SpringUtil.getBean("redisCacheService", CacheService.class);
    }
}
