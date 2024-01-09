package cn.fanzy.atfield.cache.configuration;

import cn.fanzy.atfield.cache.CacheService;
import cn.fanzy.atfield.cache.property.CacheProperty;
import cn.fanzy.atfield.core.exception.GlobalException;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
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

    @Bean(name = "cacheService")
    @ConditionalOnMissingBean(name = "cacheService")
    public CacheService cacheService(CacheProperty property) {
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

    private CacheService getLocalCacheService() {
        log.debug("启用 <全局缓存-内存> 相关的配置。");
        return SpringUtil.getBean("localCacheService", CacheService.class);
    }

    private CacheService getRedisCacheService() {
        log.debug("启用 <全局缓存-Redis> 相关的配置。");
        return SpringUtil.getBean("redisCacheService", CacheService.class);
    }
}
