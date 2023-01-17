package cn.fanzy.breeze.admin.config;

import cn.fanzy.breeze.admin.module.auth.config.BreezeAdminAuthConfig;
import cn.fanzy.breeze.admin.module.config.BreezeAdminModuleConfig;
import cn.fanzy.breeze.admin.properties.BreezeAdminProperties;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import cn.fanzy.breeze.web.swagger.properties.BreezeSwaggerProperties;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.List;

@ImportAutoConfiguration(value = {BreezeAdminSwaggerConfig.class, BreezeAdminAuthConfig.class, BreezeAdminModuleConfig.class})
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(BreezeAdminProperties.class)
@PropertySource(value = "classpath:application-admin.properties")
public class BreezeAdminConfig {

    private final BreezeAdminProperties properties;

    private final BreezeSwaggerProperties breezeSwaggerProperties;

    @Bean
    @ConditionalOnMissingBean
    public BreezeLogCallbackService breezeLogCallbackService() {
        return new BreezeAdminLoginCallbackService(properties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "breezeDefaultApi")
    public GroupedOpenApi breezeDefaultApi() {
        List<String> packagesToScan = breezeSwaggerProperties.getPackagesToScan();
        if (packagesToScan == null) {
            packagesToScan = CollUtil.newArrayList("cn.fanzy.breeze.minio.controller", "cn.fanzy.breeze.admin.module");
        } else {
            packagesToScan.add("cn.fanzy.breeze.admin.module");
            packagesToScan.add("cn.fanzy.breeze.minio.controller");
        }
        return GroupedOpenApi.builder()
                .group("-默认分组-")
                .pathsToMatch("/**")
                .packagesToScan(packagesToScan.toArray(new String[packagesToScan.size()]))
                .build();
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin组件> 相关的配置。");
    }
}
