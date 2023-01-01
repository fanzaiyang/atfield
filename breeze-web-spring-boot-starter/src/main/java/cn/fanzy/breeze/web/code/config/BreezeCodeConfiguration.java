package cn.fanzy.breeze.web.code.config;


import cn.fanzy.breeze.core.cache.config.BreezeCacheConfiguration;
import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.web.code.aop.BreezeCodeCheckAop;
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
import cn.fanzy.breeze.web.code.sender.BreezeDefaultSmsSendHandler;
import cn.fanzy.breeze.web.code.sender.BreezeSmsSendHandler;
import cn.fanzy.breeze.web.code.sender.impl.BreezeImageCodeSender;
import cn.fanzy.breeze.web.code.sender.impl.BreezeSmsCodeSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 验证码组件自动配置
 *
 * @author fanzaiyang
 * @version 2021/09/07
 */
@Slf4j
@AllArgsConstructor
@Configuration
@ImportAutoConfiguration({BreezeMailExtendAutoConfiguration.class})
@EnableConfigurationProperties({BreezeCodeProperties.class})
@AutoConfigureAfter(value = {BreezeCacheConfiguration.class})
@ConditionalOnProperty(prefix = "breeze.web.code", name = {"enable"}, havingValue = "true",matchIfMissing = true)
public class BreezeCodeConfiguration {

    private final BreezeCodeProperties properties;

    /**
     * 注入一个名为codeRepository的验证码存储器
     *
     * @return 名为codeRepository的验证码存储器
     */
    @Bean
    @ConditionalOnMissingBean(BreezeCodeRepository.class)
    public BreezeCodeRepository repository(BreezeCacheService breezeCacheService) {
        return new BreezeSimpleCodeRepository(properties, breezeCacheService);
    }

    /**
     * 注入一个缺省的图形验证码生成器
     *
     * @return 图形验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean(name = "breezeImageCodeGenerator")
    public BreezeCodeGenerator breezeImageCodeGenerator() {
        return new BreezeImageCodeGenerator();
    }

    /**
     * 注入一个缺省的图像验证码发送器
     *
     * @return 图像验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean(name = "breezeImageCodeSender")
    public BreezeCodeSender breezeImageCodeSender() {
        return new BreezeImageCodeSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public BreezeSmsSendHandler breezeSmsSendHandler() {
        return new BreezeDefaultSmsSendHandler();
    }
    /**
     * 注入体格缺省的短信验证码发送器
     *
     * @return 短信验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean(name = "breezeSmsCodeSender")
    public BreezeCodeSender breezeSmsCodeSender(BreezeSmsSendHandler breezeSmsSendHandler) {
        return new BreezeSmsCodeSender(breezeSmsSendHandler);
    }

    /**
     * 注入一个缺省的短信验证码生成器
     *
     * @return 短信验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean(name = "breezeSmsCodeGenerator")
    public BreezeCodeGenerator breezeSmsCodeGenerator() {
        return new BreezeSmsCodeGenerator();
    }

    /**
     * 注入一个缺省的邮件验证码生成器
     *
     * @return 邮件验证码生成器
     */
    @Bean
    @ConditionalOnMissingBean(name = "breezeEmailCodeGenerator")
    @ConditionalOnBean(name = "breezeEmailCodeGenerator")
    public BreezeCodeGenerator breezeEmailCodeGenerator() {
        return new BreezeEmailCodeGenerator();
    }

    /**
     * 注入一个验证码处理器
     *
     * @return 验证码处理器
     */
    @Bean
    @ConditionalOnMissingBean({BreezeCodeProcessor.class})
    public BreezeCodeProcessor codeProcessor(Map<String, BreezeCodeGenerator> codeGenerators, Map<String, BreezeCodeSender> codeSenders,BreezeCodeRepository repository) {
        return new BreezeCodeDefaultProcessor(codeGenerators, codeSenders, repository, properties);
    }

    @Bean
    @ConditionalOnMissingBean({BreezeCodeCheckAop.class})
    public BreezeCodeCheckAop breezeCodeCheckAop(BreezeCodeProcessor codeProcessor, HttpServletRequest request) {
        return new BreezeCodeCheckAop(properties, codeProcessor, request);
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <验证码支持> 相关的配置。");
    }

}
