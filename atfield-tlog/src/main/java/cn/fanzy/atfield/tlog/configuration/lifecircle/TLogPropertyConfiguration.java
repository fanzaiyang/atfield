package cn.fanzy.atfield.tlog.configuration.lifecircle;

import cn.fanzy.atfield.tlog.common.spring.TLogPropertyInit;
import cn.fanzy.atfield.tlog.configuration.TLogCommonAutoConfiguration;
import cn.fanzy.atfield.tlog.configuration.property.TLogProperty;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TLog的参数自动装配类
 *
 * @author Bryan.Zhang
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(TLogProperty.class)
@AutoConfigureAfter(TLogCommonAutoConfiguration.class)
@PropertySource(
        name = "TLog Default Properties",
        value = "classpath:/META-INF/tlog-default.properties")
public class TLogPropertyConfiguration {

    @Bean
    @ConditionalOnMissingBean(TLogPropertyInit.class)
    public TLogPropertyInit tLogPropertyInit(TLogProperty tLogProperty) {
        TLogPropertyInit tLogPropertyInit = new TLogPropertyInit();
        tLogPropertyInit.setPattern(tLogProperty.getPattern());
        tLogPropertyInit.setEnableInvokeTimePrint(tLogProperty.isEnableInvokeTimePrint());
        tLogPropertyInit.setIdGenerator(tLogProperty.getIdGenerator());
        tLogPropertyInit.setMdcEnable(tLogProperty.getMdcEnable());
        return tLogPropertyInit;
    }
}
