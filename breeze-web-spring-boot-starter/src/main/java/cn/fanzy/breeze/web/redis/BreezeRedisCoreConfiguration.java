package cn.fanzy.breeze.web.redis;

import cn.fanzy.breeze.web.code.config.BreezeCodeConfiguration;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import javax.annotation.PostConstruct;
import java.time.Duration;


/**
 * Redis扩展支持自动配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass(RedisOperations.class)
@AutoConfigureBefore(value = {BreezeCodeConfiguration.class})
public class BreezeRedisCoreConfiguration extends CachingConfigurerSupport {
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisSerializationContext.SerializationPair serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(30))
                .serializeValuesWith(serializationPair);
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
    /**
     * 注入一个Redis序列化器
     *
     * @return Redis序列化器
     */
    @Bean("redisValueSerializer")
    @ConditionalOnMissingBean(name = "redisValueSerializer")
    public RedisSerializer<Object> redisValueSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 反序列化时候遇到不匹配的属性并不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化时候遇到空对象不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化的时候如果是无效子类型,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        // 不使用默认的dateTime进行序列化,
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 配置null值的序列化器
//		GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 注入一个Redis操作工具
     *
     * @param redisConnectionFactory    连接工厂
     * @return Redis操作工具
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(redisValueSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(redisValueSerializer());
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * <p>自定义一个名字为springSessionDefaultRedisSerializer 的序列化器</p>
     * 参见
     * org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration
     * 的188行
     *
     * @return Redis序列化器
     */
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new JdkSerializationRedisSerializer();

    }

    /**
     * 自定义Redis缓存配置
     *
     * @param redisValueSerializer Redis序列化器
     * @return Redis缓存配置
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisCacheConfiguration redisCacheConfiguration(RedisSerializer<Object> redisValueSerializer) {
        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        // spring Security 默认不支持 jackson的序列化
                        .fromSerializer(redisValueSerializer))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .entryTtl(Duration.ofMinutes(30L));
        return configuration;
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <Redis扩展支持> 相关的配置。");
    }

}
