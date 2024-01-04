package cn.fanzy.infra.repository.sqltoy.configuration;

import cn.fanzy.infra.repository.sqltoy.handler.DefaultUnifyFieldsHandler;
import cn.fanzy.infra.repository.sqltoy.model.AnonymousUserDetails;
import cn.fanzy.infra.repository.sqltoy.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * SQLTOY 自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/22
 */
@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:sqltoy.properties")
public class SqltoyAutoConfiguration {

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

}
