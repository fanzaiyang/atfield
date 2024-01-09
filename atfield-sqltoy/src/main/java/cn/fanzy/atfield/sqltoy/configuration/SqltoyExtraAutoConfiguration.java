package cn.fanzy.atfield.sqltoy.configuration;

import cn.fanzy.atfield.sqltoy.entity.AnonymousUserOnlineInfo;
import cn.fanzy.atfield.sqltoy.entity.IUserOnlineInfo;
import cn.fanzy.atfield.sqltoy.handler.DefaultUnifyFieldsHandler;
import cn.fanzy.atfield.sqltoy.repository.BaseRepository;
import cn.fanzy.atfield.sqltoy.repository.BaseRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SQLTOY 额外自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@RequiredArgsConstructor
@Configuration
public class SqltoyExtraAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public IUserOnlineInfo userOnlineInfo() {
        return new AnonymousUserOnlineInfo();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUnifyFieldsHandler unifyFieldsHandler(IUserOnlineInfo userOnlineInfo) {
        return new DefaultUnifyFieldsHandler(userOnlineInfo);
    }

    @Bean
    public BaseRepository baseRepository() {
        return new BaseRepositoryImpl();
    }
}
