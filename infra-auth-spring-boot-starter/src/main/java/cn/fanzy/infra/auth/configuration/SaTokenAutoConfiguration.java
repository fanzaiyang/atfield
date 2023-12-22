package cn.fanzy.infra.auth.configuration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * SA 令牌配置
 *
 * @author fanzaiyang
 * @date 2023/12/18
 */
@Configuration
@ImportAutoConfiguration({SaTokenAnnotationConfiguration.class,
        SaTokenRouteConfiguration.class})
public class SaTokenAutoConfiguration {

}
