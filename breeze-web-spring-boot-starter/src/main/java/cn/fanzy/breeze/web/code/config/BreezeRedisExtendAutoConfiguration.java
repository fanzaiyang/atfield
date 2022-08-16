package cn.fanzy.breeze.web.code.config;

import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import cn.fanzy.breeze.web.code.repository.impl.BreezeRedisCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;


/**
 * 基于Redis的验证码存储器自动配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@ConditionalOnProperty(prefix = "breeze.code", name = {"enable"}, havingValue = "true", matchIfMissing = false)
public class BreezeRedisExtendAutoConfiguration {

    /**
     * 注入一个名字为codeRepository验证码存储器
     *
     * @param redisTemplate  RedisTemplate
     * @param codeProperties 验证码属性配置
     * @return 名字为codeRepository验证码存储器
     */
    @ConditionalOnBean(name = "redisTemplate")
    @ConditionalOnMissingBean({BreezeCodeRepository.class})
    @Bean
    public BreezeCodeRepository redisRepository(RedisTemplate<String, Object> redisTemplate, BreezeCodeProperties codeProperties) {
        return new BreezeRedisCodeRepository(redisTemplate, codeProperties);
    }
    @PostConstruct
    public void init() {
        log.info("【微风组件】: 开启 <验证码-redis> 相关的配置");
    }
}
