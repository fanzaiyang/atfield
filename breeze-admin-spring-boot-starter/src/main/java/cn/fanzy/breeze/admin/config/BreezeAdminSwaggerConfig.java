package cn.fanzy.breeze.admin.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Configuration
public class BreezeAdminSwaggerConfig {
    protected final SpringDocConfigProperties springDocConfigProperties;
    @Bean
    public GroupedOpenApi breezeAdminApi(){
        return GroupedOpenApi.builder()
                .group("微风组件")
                .pathsToMatch("/**")
                .packagesToScan("cn.fanzy.breeze.admin")
                .build();
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Swagger3 for Admin> 相关的配置。");
    }
}
