package cn.fanzy.infra.log.configuration;

import cn.fanzy.infra.log.configuration.property.TLogProperty;
import cn.fanzy.infra.log.print.callback.DefaultLogCallbackServiceImpl;
import cn.fanzy.infra.log.print.callback.LogCallbackService;
import cn.fanzy.infra.log.print.advice.TLogWebInvokeTimeAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * tlog打印自动配置
 *
 * @author Bryan.Zhang
 * @date 2023/12/06
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties({TLogProperty.class})
public class TLogPrintAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LogCallbackService logCallbackService(TLogProperty property) {
        return new DefaultLogCallbackServiceImpl(property);
    }

    @Bean
    public TLogWebInvokeTimeAdvice tLogWebInvokeTimeAdvice(TLogProperty property, LogCallbackService callbackService) {
        return new TLogWebInvokeTimeAdvice(property, callbackService);
    }
}
