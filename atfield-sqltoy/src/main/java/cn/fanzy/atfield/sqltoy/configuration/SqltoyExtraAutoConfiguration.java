package cn.fanzy.atfield.sqltoy.configuration;

import cn.fanzy.atfield.core.model.IOperator;
import cn.fanzy.atfield.sqltoy.delflag.interceptor.LogicDelFilterInterceptor;
import cn.fanzy.atfield.sqltoy.entity.AnonymousCurrentUserInfo;
import cn.fanzy.atfield.sqltoy.entity.ICurrentUserInfo;
import cn.fanzy.atfield.sqltoy.handler.DefaultUnifyFieldsHandler;
import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import cn.fanzy.atfield.sqltoy.repository.impl.SqlToyRepositoryImpl;
import com.sagframe.sagacity.sqltoy.plus.EnableSqlToyPlus;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * SQLTOY 额外自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@EnableSqlToyPlus
@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:atfield-sqltoy.properties")
@EnableConfigurationProperties(SqltoyExtraProperties.class)
public class SqltoyExtraAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ICurrentUserInfo currentUserInfo() {
        return new AnonymousCurrentUserInfo();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.sqltoy.extra", name = {"unifyField"}, havingValue = "true", matchIfMissing = true)
    public IUnifyFieldsHandler unifyFieldsHandler(IOperator IOperator, SqltoyExtraProperties properties) {
        return new DefaultUnifyFieldsHandler(IOperator, properties);
    }

    @Qualifier("logicDelFilterInterceptor")
    @Bean("logicDelFilterInterceptor")
    @ConditionalOnMissingBean
    public LogicDelFilterInterceptor logicDelFilterInterceptor(SqltoyExtraProperties properties) {
        return new LogicDelFilterInterceptor(properties);
    }

    @Primary
    @Bean
    @ConditionalOnMissingBean
    public SqlToyRepository sqlToyRepository(SqltoyExtraProperties properties) {
        return new SqlToyRepositoryImpl(properties);
    }
}
