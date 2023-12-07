package cn.fanzy.infra.web.configuration;

import cn.fanzy.infra.web.advice.GlobalExceptionAdvice;
import cn.fanzy.infra.web.advice.ResponseWrapperAdvice;
import cn.fanzy.infra.web.filter.ReplaceStreamFilter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * infra-web自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@Configuration
@ImportAutoConfiguration({ReplaceStreamFilter.class, GlobalExceptionAdvice.class, ResponseWrapperAdvice.class})
@PropertySource(
        name = "TLog Default framework Properties",
        value = "classpath:/META-INF/infra-web-default.properties")
public class InfraWebAutoConfiguration {
}
