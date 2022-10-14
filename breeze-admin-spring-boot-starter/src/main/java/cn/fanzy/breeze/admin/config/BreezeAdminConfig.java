package cn.fanzy.breeze.admin.config;

import cn.fanzy.breeze.admin.module.auth.config.BreezeAdminAuthConfig;
import cn.fanzy.breeze.admin.module.config.BreezeAdminModuleConfig;
import cn.fanzy.breeze.admin.properties.BreezeAdminProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(value = {BreezeSwagger2Config.class, BreezeAdminAuthConfig.class, BreezeAdminModuleConfig.class})
@Slf4j
@Configuration
@EnableConfigurationProperties(BreezeAdminProperties.class)
@PropertySource(value = "classpath:application-admin.properties")
@EnableSwagger2WebMvc
public class BreezeAdminConfig {

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin组件> 相关的配置。");
    }
}
