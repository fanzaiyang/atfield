package cn.fanzy.breeze.sqltoy.config;

import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDaoImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqltoyLightHelperDao;
import cn.fanzy.breeze.sqltoy.plus.dao.SqltoyLightHelperDaoImpl;
import cn.fanzy.breeze.sqltoy.plus.handler.BreezeSqlToyUnifyFieldsHandler;
import cn.fanzy.breeze.sqltoy.properties.BreezeSqlToyProperties;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.configure.SqlToyContextProperties;
import org.sagacity.sqltoy.configure.SqltoyAutoConfiguration;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 微风sql玩具+配置
 *
 * @author fanzaiyang
 * @since  2023-03-16
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@AutoConfigureBefore({SqltoyAutoConfiguration.class})
@EnableConfigurationProperties({BreezeSqlToyProperties.class})
public class BreezeSqlToyPlusConfig {
    private final SqlToyContextProperties sqlToyContextProperties;
    private final BreezeSqlToyProperties properties;
    @Bean
    public SqlToyHelperDao sqlToyHelperDao() {
        return new SqlToyHelperDaoImpl();
    }
    @Bean
    public SqltoyLightHelperDao sqltoyLightHelperDao(){
        return new SqltoyLightHelperDaoImpl();
    }
    @Bean
    @ConditionalOnMissingBean
    public IUnifyFieldsHandler unifyFieldsHandler() {
        return new BreezeSqlToyUnifyFieldsHandler();
    }
//    @Primary
//    @Bean
//    @ConditionalOnMissingBean
//    public SqlToyContextProperties sqlToyContextProperties(BreezeSqlToyProperties properties) {
//        SqlToyContextProperties sqlToyContextProperties = new SqlToyContextProperties();
//        if(StrUtil.isNotBlank(properties.getLogicDeleteField())){
//            String[] append = ArrayUtil.append(sqlToyContextProperties.getSqlInterceptors(),
//                    "cn.fanzy.breeze.sqltoy.interceptors.LogicalDeleteInterceptor");
//            sqlToyContextProperties.setSqlInterceptors(append);
//        }
//        return sqlToyContextProperties;
//    }

    @PostConstruct
    public void checkConfig() {
//        if(StrUtil.isNotBlank(properties.getLogicDeleteField())){
//            String[] append = ArrayUtil.append(sqlToyContextProperties.getSqlInterceptors(),
//                    "cn.fanzy.breeze.sqltoy.interceptors.LogicalDeleteInterceptor");
//            sqlToyContextProperties.setSqlInterceptors(append);
//        }
        if(StrUtil.isBlank(sqlToyContextProperties.getSqlResourcesDir())){
            sqlToyContextProperties.setSqlResourcesDir("classpath:sql");
        }
        if(StrUtil.isBlank(sqlToyContextProperties.getUnifyFieldsHandler())){
            sqlToyContextProperties.setUnifyFieldsHandler("unifyFieldsHandler");
        }
        log.info("「微风组件」开启 <SqlToy相关配置> 相关的配置。");
    }
}
