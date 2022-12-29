package cn.fanzy.breeze.admin.config;

import cn.fanzy.breeze.admin.module.auth.config.BreezeAdminAuthConfig;
import cn.fanzy.breeze.admin.module.config.BreezeAdminModuleConfig;
import cn.fanzy.breeze.admin.properties.BreezeAdminProperties;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(value = {SwaggerConfig.class,BreezeAdminAuthConfig.class, BreezeAdminModuleConfig.class})
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(BreezeAdminProperties.class)
@PropertySource(value = "classpath:application-admin.properties")
public class BreezeAdminConfig {

    private final BreezeAdminProperties properties;
    @Bean
    @ConditionalOnMissingBean
    public BreezeLogCallbackService breezeLogCallbackService(){
        return new BreezeAdminLoginCallbackService(properties);
    }
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin组件> 相关的配置。");
    }
}
