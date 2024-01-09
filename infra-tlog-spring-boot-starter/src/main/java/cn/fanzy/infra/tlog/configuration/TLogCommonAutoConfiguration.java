package cn.fanzy.infra.tlog.configuration;

import cn.fanzy.infra.tlog.common.spring.TLogSpringAware;
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
