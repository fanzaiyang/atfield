package cn.fanzy.infra.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * infra-web自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@Configuration
@PropertySource(
        name = "TLog Default framework Properties",
        value = "classpath:/META-INF/infra-web-default.properties")
public class InfraWebAutoConfiguration {
}
