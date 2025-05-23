package cn.fanzy.atfield.core.config;

import cn.fanzy.atfield.core.model.IOperator;
import cn.fanzy.atfield.core.operator.AnonymousIOperator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SQLTOY 额外自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class OperatorAnonymousConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public IOperator operator() {
        return new AnonymousIOperator();
    }

    @PostConstruct
    public void init() {
        log.info("开启 <operator> Anonymous相关的配置.");
    }
}
