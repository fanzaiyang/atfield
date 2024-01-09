package cn.fanzy.atfield.web.configuration;

import cn.fanzy.atfield.web.cors.CorsProperties;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author fanzaiyang
 */
@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({CorsProperties.class})
@ConditionalOnProperty(prefix = "atfield.web.cors", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class CorsAutoConfiguration {
    private final CorsProperties corsProperties;

    /**
     * 配置跨域支持
     *
     * @return WebMvcConfigurer
     */
    @Bean("corsAllowedConfigurer")
    @ConditionalOnMissingBean(name = "corsAllowedConfigurer")
    public WebMvcConfigurer corsAllowedConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //@formatter:off
                registry
                        .addMapping(corsProperties.getUrl())
                        .allowedOriginPatterns(corsProperties.getAllowedOrigins())
                        .allowedMethods(corsProperties.getAllowedMethods())
                        .allowedHeaders(corsProperties.getAllowedHeaders())
                        .allowCredentials(corsProperties.getAllowCredentials());//允许带认证信息的配置
                //@formatter:on
            }
        };
    }

    /**
     * 注入一个跨域支持过滤器
     *
     * @return 跨域支持过滤器
     */
    @Bean("corsAllowedFilter")
    @ConditionalOnMissingBean(name = "corsAllowedFilter")
    public CorsFilter corsAllowedFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader(corsProperties.getAllowedHeaders());
        corsConfiguration.addAllowedMethod(corsProperties.getAllowedMethods());
        corsConfiguration.addAllowedOriginPattern(corsProperties.getAllowedOrigins());
        source.registerCorsConfiguration(corsProperties.getUrl(), corsConfiguration);

        return new CorsFilter(source);
    }


    /**
     * 配置检查
     */
    @PostConstruct
    public void checkConfig() {
        log.info("开启 <跨域支持> 相关的配置");
    }
}
