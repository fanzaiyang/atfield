package cn.fanzy.breeze.web.cache.config;

import cn.fanzy.breeze.web.cache.properties.BreezeCacheProperties;
import cn.fanzy.breeze.web.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.cache.service.impl.BreezeRedisCacheService;
import cn.fanzy.breeze.web.redis.RedisCoreConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 基于Redis的验证码存储器自动配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureAfter(RedisCoreConfiguration.class)
@EnableConfigurationProperties(BreezeCacheProperties.class)
@ConditionalOnProperty(prefix = "breeze.web.cache", name = {"type"}, havingValue = "redis")
public class BreezeRedisCacheConfiguration {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public BreezeCacheService breezeCacheService(RedisTemplate<String, Object> redisTemplate) {
        log.info("「微风组件」开启 <全局缓存Redis> 相关的配置。");
        return new BreezeRedisCacheService(redisTemplate);
    }
}
