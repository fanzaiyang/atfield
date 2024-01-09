package cn.fanzy.atfield.satoken.configuration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * SA 令牌自动配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Configuration
@ImportAutoConfiguration({SaTokenAnnotationConfiguration.class,
        SaTokenRouteConfiguration.class})
public class SaTokenAutoConfiguration {
}
