package cn.fanzy.breeze.web.code.config;


import cn.fanzy.breeze.web.code.generator.BreezeCodeGenerator;
import cn.fanzy.breeze.web.code.generator.impl.BreezeEmailCodeGenerator;
import cn.fanzy.breeze.web.code.generator.impl.BreezeImageCodeGenerator;
import cn.fanzy.breeze.web.code.generator.impl.BreezeSmsCodeGenerator;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.code.processor.impl.BreezeCodeDefaultProcessor;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.repository.BreezeCodeRepository;
import cn.fanzy.breeze.web.code.repository.impl.BreezeSimpleCodeRepository;
import cn.fanzy.breeze.web.code.sender.BreezeCodeSender;
import cn.fanzy.breeze.web.code.sender.impl.BreezeImageCodeSender;
import cn.fanzy.breeze.web.code.sender.impl.BreezeSmsCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
@ConditionalOnProperty(prefix = "breeze.code", name = {"enable"}, havingValue = "true", matchIfMissing = false)
public class BreezeCodeAutoConfiguration {

    /**
     * 注入一个名为codeRepository的验证码存储器
     *
     * @return 名为codeRepository的验证码存储器
     */

    @Bean
    @ConditionalOnMissingBean(name = {"redisTemplate"}, value = {BreezeCodeRepository.class})
    public BreezeCodeRepository repository() {
        return new BreezeSimpleCodeRepository();
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
     * @return 验证码处理器
     */
    @Bean
    @ConditionalOnMissingBean({BreezeCodeProcessor.class})
    public BreezeCodeProcessor codeProcessor(Map<String, BreezeCodeGenerator> codeGenerators,Map<String, BreezeCodeSender> codeSenders,
                                             BreezeCodeProperties codeProperties) {
        return new BreezeCodeDefaultProcessor(codeGenerators, codeSenders, repository(), codeProperties);
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void init() {
        log.info("「微风组件」: 开启 <验证码支持> 相关的配置");
    }

}
