package cn.fanzy.breeze.sqltoy.config;

import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDaoImpl;
import cn.fanzy.breeze.sqltoy.plus.handler.BreezeSqlToyUnifyFieldsHandler;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
@Slf4j
@Configuration
@PropertySource(value = {"classpath:application-sqltoy.properties"})
public class BreezeSqlToyPlusConfig {
    @Bean
    public SqlToyHelperDao sqlToyHelperDao() {
        return new SqlToyHelperDaoImpl();
    }
    @Bean
    @ConditionalOnMissingBean
    public IUnifyFieldsHandler unifyFieldsHandler() {
        return new BreezeSqlToyUnifyFieldsHandler();
    }
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <SqlToy相关配置> 相关的配置。");
    }
}
