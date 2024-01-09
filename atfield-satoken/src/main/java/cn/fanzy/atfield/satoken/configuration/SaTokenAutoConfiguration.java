package cn.fanzy.atfield.satoken.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.fanzy.atfield.satoken.property.SaTokenExtraProperty;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SA 令牌自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Configuration
@EnableConfigurationProperties(SaTokenExtraProperty.class)
@ImportAutoConfiguration({SaTokenAnnotationConfiguration.class,
        SaTokenRouteConfiguration.class})
public class SaTokenAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "atfield.sa-token")
    public SaTokenExtraProperty saTokenExtraProperty() {
        return new SaTokenExtraProperty();
    }
}
