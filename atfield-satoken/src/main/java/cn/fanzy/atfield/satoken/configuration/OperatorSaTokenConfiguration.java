package cn.fanzy.atfield.satoken.configuration;

import cn.fanzy.atfield.core.config.OperatorAnonymousConfiguration;
import cn.fanzy.atfield.core.model.IOperator;
import cn.fanzy.atfield.satoken.context.StpContext;
import cn.fanzy.atfield.satoken.operator.SaTokenIOperator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * SQLTOY 额外自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@AutoConfigureBefore(OperatorAnonymousConfiguration.class)
@ConditionalOnClass(StpContext.class)
public class OperatorSaTokenConfiguration {
    @Primary
    @Bean
    @ConditionalOnMissingBean
    public IOperator operator() {
        return new SaTokenIOperator();
    }

    @PostConstruct
    public void init() {
        log.info("开启 <operator> SaToken相关的配置.");
    }
}
