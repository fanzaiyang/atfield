package cn.fanzy.infra.cache.configuration;

import cn.fanzy.infra.cache.impl.LocalCacheServiceImpl;
import cn.fanzy.infra.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class LocalCacheAutoConfiguration {
    @Bean(name = "localCacheService")
    @ConditionalOnMissingBean(name = "localCacheService")
    public CacheService localCacheService() {
        log.info("开启 <全局内存-内存> 相关的配置。");
        return new LocalCacheServiceImpl();
    }
}
