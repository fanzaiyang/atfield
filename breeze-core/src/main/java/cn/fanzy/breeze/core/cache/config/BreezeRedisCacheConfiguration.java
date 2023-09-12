package cn.fanzy.breeze.core.cache.config;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.core.cache.service.impl.BreezeRedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * 基于Redis的验证码存储器自动配置
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore(BreezeMemoryCacheConfiguration.class)
public class BreezeRedisCacheConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public BreezeCacheService breezeCacheService(StringRedisTemplate redisTemplate) {
        log.info("「微风组件」开启 <全局缓存Redis> 相关的配置。");
        return new BreezeRedisCacheService(redisTemplate);
    }
}
