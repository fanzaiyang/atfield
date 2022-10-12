package cn.fanzy.breeze.admin.config;

import cn.fanzy.breeze.admin.config.handler.BreezeSqlToyUnifyFieldsHandler;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.plugins.IUnifyFieldsHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@PropertySource(value = "classpath:application-admin.properties")
public class BreezeAdminConfig {

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin相关配置> 相关的配置。");
    }
}
