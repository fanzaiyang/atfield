package cn.fanzy.atfield.tlog.configuration;


import cn.fanzy.atfield.tlog.print.advice.TLogWebInvokeTimeAdvice;
import cn.fanzy.atfield.tlog.print.callback.DefaultLogCallbackServiceImpl;
import cn.fanzy.atfield.tlog.print.callback.DefaultLogUserCallbackServiceImpl;
import cn.fanzy.atfield.tlog.print.callback.LogCallbackService;
import cn.fanzy.atfield.tlog.print.callback.LogUserCallbackService;
import cn.fanzy.atfield.tlog.configuration.property.TLogProperty;
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
    @ConditionalOnMissingBean
    public TLogWebInvokeTimeAdvice tLogWebInvokeTimeAdvice(TLogProperty property, LogCallbackService callbackService,
                                                           LogUserCallbackService logUserCallbackService) {
        return new TLogWebInvokeTimeAdvice(property, callbackService, logUserCallbackService);
    }
}
