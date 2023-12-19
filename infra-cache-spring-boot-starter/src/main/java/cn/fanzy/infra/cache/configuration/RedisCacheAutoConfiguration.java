package cn.fanzy.infra.cache.configuration;

import cn.fanzy.infra.cache.CacheService;
import cn.fanzy.infra.cache.impl.RedisCacheServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 本地缓存配置
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisCacheAutoConfiguration {

    @Bean(name = "redisCacheService")
    @ConditionalOnMissingBean(name = "redisCacheService")
    public CacheService redisCacheService(RedisTemplate<Object, Object> redisTemplate) {
        log.info("开启 <全局内存Redis> 相关的配置。");
        return new RedisCacheServiceImpl(redisTemplate);
    }
}
