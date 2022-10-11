package cn.fanzy.breeze.web.logs.config;

import cn.fanzy.breeze.web.logs.aop.BreezeLogsAop;
import cn.fanzy.breeze.web.logs.properties.BreezeLogsProperties;
import cn.fanzy.breeze.web.logs.service.BreezeDefaultLogCallbackService;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeLogsProperties.class})
@AutoConfigureBefore(BreezeLogsAop.class)
@ConditionalOnProperty(prefix = "breeze.web.log", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeLogsConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public BreezeLogCallbackService breezeIpProperties() {
        return new BreezeDefaultLogCallbackService();
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启<全局日志>相关的配置。");
    }
}
