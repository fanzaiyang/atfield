package cn.fanzy.field.tlog.configuration;

import cn.fanzy.field.tlog.common.spring.TLogSpringAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TLogCommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TLogSpringAware.class)
    public TLogSpringAware tLogSpringAware(){
        return new TLogSpringAware();
    }
}
