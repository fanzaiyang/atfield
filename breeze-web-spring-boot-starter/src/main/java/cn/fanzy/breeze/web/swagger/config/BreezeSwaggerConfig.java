package cn.fanzy.breeze.web.swagger.config;

import cn.fanzy.breeze.web.swagger.properties.BreezeSwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeSwaggerProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.swagger", name = {"enable"}, havingValue = "true", matchIfMissing = false)
public class BreezeSwaggerConfig implements WebMvcConfigurer {
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

    @Bean
    @ConditionalOnMissingBean(name = "breezeDefaultApi")
    @ConditionalOnProperty(prefix = "breeze.web.swagger", name = {"packages-to-scan"})
    public GroupedOpenApi breezeDefaultApi() {
        return GroupedOpenApi.builder()
                .group("-默认分组-")
                .pathsToMatch("/**")
                .packagesToScan(properties.getPackagesToScan().toArray(new String[properties.getPackagesToScan().size()]))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 配置knife4j 显示文档
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 配置swagger-ui显示文档
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 公共部分内容
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/");
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Swagger3> 相关的配置。");
    }
}
