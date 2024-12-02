package cn.fanzy.atfield.sqltoy.configuration;

import cn.fanzy.atfield.core.model.Operator;
import cn.fanzy.atfield.satoken.context.StpContext;
import cn.fanzy.atfield.sqltoy.operator.SaTokenOperator;
import com.sagframe.sagacity.sqltoy.plus.EnableSqlToyPlus;
import lombok.RequiredArgsConstructor;
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
@EnableSqlToyPlus
@RequiredArgsConstructor
@Configuration
@AutoConfigureBefore(SqltoyAnyOperatorConfiguration.class)
@ConditionalOnClass(StpContext.class)
public class SqltoySaOperatorConfiguration {
    @Primary
    @Bean
    @ConditionalOnMissingBean
    public Operator operator() {
        return new SaTokenOperator();
    }
}
