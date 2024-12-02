package cn.fanzy.atfield.sqltoy.configuration;

import cn.fanzy.atfield.core.model.Operator;
import cn.fanzy.atfield.sqltoy.operator.AnonymousOperator;
import com.sagframe.sagacity.sqltoy.plus.EnableSqlToyPlus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SQLTOY 额外自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@EnableSqlToyPlus
@RequiredArgsConstructor
@Configuration
@AutoConfigureAfter(SqltoySaOperatorConfiguration.class)
public class SqltoyAnyOperatorConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public Operator operator() {
        return new AnonymousOperator();
    }
}
