package cn.fanzy.breeze.web.swagger.config;

import cn.fanzy.breeze.web.swagger.properties.BreezeSwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeSwaggerProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.swagger", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeSwaggerConfig {
    private final BreezeSwaggerProperties properties;
    @Bean
    public OpenAPI breezeCustomOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.getTitle())
                        .summary(properties.getSummary())
                        .contact(properties.getContact())
                        .version(properties.getVersion())
                        .description(properties.getDescription())
                        .termsOfService(properties.getTermsOfService())
                        .license(properties.getLicense())
                        .extensions(properties.getExtensions())
                );
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Swagger3> 相关的配置。");
    }
}
