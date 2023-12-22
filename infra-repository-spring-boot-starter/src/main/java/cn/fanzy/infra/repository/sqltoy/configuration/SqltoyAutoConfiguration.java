package cn.fanzy.infra.repository.sqltoy.configuration;

import cn.fanzy.infra.repository.sqltoy.handler.DefaultUnifyFieldsHandler;
import cn.fanzy.infra.repository.sqltoy.model.AnonymousUserDetails;
import cn.fanzy.infra.repository.sqltoy.model.UserDetails;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.configure.SqlToyContextProperties;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.sagacity.sqltoy.utils.StringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SQLTOY 自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/22
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(SqlToyContextProperties.class)
public class SqltoyAutoConfiguration {
    private final SqlToyContextProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public UserDetails userDetails() {
        return new AnonymousUserDetails();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUnifyFieldsHandler unifyFieldsHandler(UserDetails userDetails) {
        return new DefaultUnifyFieldsHandler(userDetails);
    }

    @PostConstruct
    public void checkConfig() {
        if (StringUtil.isBlank(properties.getSqlResourcesDir())) {
            properties.setSqlResourcesDir("classpath:sql");
        }
        if (StringUtil.isBlank(properties.getUnifyFieldsHandler())) {
            properties.setUnifyFieldsHandler("unifyFieldsHandler");
        }
    }
}
