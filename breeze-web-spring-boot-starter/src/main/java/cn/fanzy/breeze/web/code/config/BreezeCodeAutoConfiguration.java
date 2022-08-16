package cn.fanzy.breeze.web.code.config;


import cn.fanzy.breeze.web.code.generator.BreezeCodeGenerator;
import cn.fanzy.breeze.web.code.generator.impl.BreezeEmailCodeGenerator;
import cn.fanzy.breeze.web.code.generator.impl.BreezeImageCodeGenerator;
import cn.fanzy.breeze.web.code.generator.impl.BreezeSmsCodeGenerator;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.code.processor.impl.BreezeCodeDefaultProcessor;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import cn.fanzy.breeze.web.code.repository.impl.BreezeRedisCodeRepository;
import cn.fanzy.breeze.web.code.sender.BreezeCodeSender;
import cn.fanzy.breeze.web.code.sender.impl.BreezeImageCodeSender;
import cn.fanzy.breeze.web.code.sender.impl.BreezeSmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.PostConstruct;
import java.util.Map;


/**
 * 验证码组件自动配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({BreezeCodeProperties.class})
@Import({BreezeMailExtendAutoConfiguration.class, BreezeRedisExtendAutoConfiguration.class})
@AutoConfigureAfter(value = {BreezeRedisExtendAutoConfiguration.class})
public class BreezeCodeAutoConfiguration {

    /**
     * 注入一个名为codeRepository的验证码存储器
     *
     * @return 名为codeRepository的验证码存储器
     */
    @ConditionalOnMissingBean(name = {"redisTemplate"}, value = {BreezeCodeRepository.class})
    @Bean
    public BreezeCodeRepository codeRepository(BreezeCodeProperties codeProperties, RedisTemplate<String, Object> redisTemplate) {
        return new BreezeRedisCodeRepository(redisTemplate, codeProperties);
    }

    /**
     * 注入一个缺省的图形验证码生成器
     *
     * @return 图形验证码生成器
     */
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    @Bean("imageCodeGenerator")
    public BreezeCodeGenerator imageCodeGenerator() {
        return new BreezeImageCodeGenerator();
    }

    /**
     * 注入一个缺省的图像验证码发送器
     *
     * @return 图像验证码发送器
     */
    @ConditionalOnMissingBean(name = "imageCodeSender")
    @Bean("imageCodeSender")
    public BreezeCodeSender imageCodeSender() {
        return new BreezeImageCodeSender();
    }

    /**
     * 注入体格缺省的短信验证码发送器
     *
     * @return 短信验证码发送器
     */
    @ConditionalOnMissingBean(name = "smsCodeSender")
    @Bean("smsCodeSender")
    public BreezeCodeSender smsCodeSender() {
        return new BreezeSmsCodeSender();
    }

    /**
     * 注入一个缺省的短信验证码生成器
     *
     * @return 短信验证码生成器
     */
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    @Bean("smsCodeGenerator")
    public BreezeCodeGenerator smsCodeGenerator() {
        return new BreezeSmsCodeGenerator();
    }

    /**
     * 注入一个缺省的邮件验证码生成器
     *
     * @return 邮件验证码生成器
     */
    @ConditionalOnMissingBean(name = "emailCodeGenerator")
    @Bean("emailCodeGenerator")
    @ConditionalOnBean(name = "emailCodeSender")
    public BreezeCodeGenerator emailCodeGenerator() {
        return new BreezeEmailCodeGenerator();
    }

    /**
     * 注入一个验证码处理器
     *
     * @param codeProperties 验证码属性配置
     * @param codeGenerators 系统中所有的 {@link BreezeCodeGenerator} 验证码生成器接口的实现。key为bean的名字
     * @param codeSenders    系统中所有的 {@link BreezeCodeSender } 验证码发送器接口的实现，。key为bean的名字
     * @param repository     验证码存储器
     * @return 验证码处理器
     */
    @Bean
    @ConditionalOnMissingBean({BreezeCodeProcessor.class})
    public BreezeCodeProcessor codeProcessor(BreezeCodeProperties codeProperties, Map<String, BreezeCodeGenerator> codeGenerators,
                                             Map<String, BreezeCodeSender> codeSenders, BreezeCodeRepository repository) {
        BreezeCodeDefaultProcessor simpleCodeProcessor = new BreezeCodeDefaultProcessor(codeGenerators, codeSenders, repository,
                codeProperties);
        return simpleCodeProcessor;
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void init() {
        log.info("【微风组件】: 开启 <验证码支持> 相关的配置");
    }

}
