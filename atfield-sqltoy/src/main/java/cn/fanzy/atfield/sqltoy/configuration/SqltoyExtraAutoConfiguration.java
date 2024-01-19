package cn.fanzy.atfield.sqltoy.configuration;

import cn.fanzy.atfield.sqltoy.entity.AnonymousCurrentUserInfo;
import cn.fanzy.atfield.sqltoy.entity.ICurrentUserInfo;
import cn.fanzy.atfield.sqltoy.handler.DefaultUnifyFieldsHandler;
import cn.fanzy.atfield.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.atfield.sqltoy.plus.dao.SqlToyHelperDaoImpl;
import cn.fanzy.atfield.sqltoy.plus.dao.SqltoyLightHelperDao;
import cn.fanzy.atfield.sqltoy.plus.dao.SqltoyLightHelperDaoImpl;
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
    public ICurrentUserInfo currentUserInfo() {
        return new AnonymousCurrentUserInfo();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUnifyFieldsHandler unifyFieldsHandler(ICurrentUserInfo currentUserInfo) {
        return new DefaultUnifyFieldsHandler(currentUserInfo);
    }
    @Bean("sqlToyHelperDao")
    @ConditionalOnMissingBean(name = "sqlToyHelperDao")
    public SqlToyHelperDao sqlToyHelperDao() {
        return new SqlToyHelperDaoImpl();
    }
    @Bean("sqltoyLightHelperDao")
    @ConditionalOnMissingBean(name = "sqltoyLightHelperDao")
    public SqltoyLightHelperDao sqltoyLightHelperDao() {
        return new SqltoyLightHelperDaoImpl();
    }
    @Bean
    public BaseRepository baseRepository() {
        return new BaseRepositoryImpl();
    }
}
