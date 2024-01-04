package cn.fanzy.infra.tlog.configuration;

import cn.fanzy.infra.tlog.configuration.property.TLogProperty;
import cn.fanzy.infra.tlog.print.advice.TLogWebInvokeTimeAdvice;
import cn.fanzy.infra.tlog.print.callback.DefaultLogCallbackServiceImpl;
import cn.fanzy.infra.tlog.print.callback.DefaultLogUserCallbackServiceImpl;
import cn.fanzy.infra.tlog.print.callback.LogCallbackService;
import cn.fanzy.infra.tlog.print.callback.LogUserCallbackService;
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
    public LogCallbackService logCallbackService() {
        return new DefaultLogCallbackServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogUserCallbackService logUserCallbackService() {
        return new DefaultLogUserCallbackServiceImpl();
    }

    @Bean
    public TLogWebInvokeTimeAdvice tLogWebInvokeTimeAdvice(TLogProperty property, LogCallbackService callbackService) {
        return new TLogWebInvokeTimeAdvice(property, callbackService);
    }
}
