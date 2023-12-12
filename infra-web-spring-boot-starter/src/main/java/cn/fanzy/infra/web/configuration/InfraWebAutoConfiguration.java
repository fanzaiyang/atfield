package cn.fanzy.infra.web.configuration;

import cn.fanzy.infra.web.advice.GlobalExceptionAdvice;
import cn.fanzy.infra.web.advice.ResponseWrapperAdvice;
import cn.fanzy.infra.web.filter.ReplaceStreamFilter;
import cn.fanzy.infra.web.json.property.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * infra-web自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@RequiredArgsConstructor
@Configuration
@AutoConfigureBefore(JsonConvertAutoConfiguration.class)
@EnableConfigurationProperties({JsonProperty.class})
@ImportAutoConfiguration({ReplaceStreamFilter.class, GlobalExceptionAdvice.class, ResponseWrapperAdvice.class})
@PropertySource(
        name = "TLog Default framework Properties",
        value = "classpath:/META-INF/infra-web-default.properties")
public class InfraWebAutoConfiguration implements WebMvcConfigurer {
    /**
     * 跨域配置会覆盖默认的配置，
     * 因此需要实现addResourceHandlers方法，增加默认配置静态路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/");
    }
}
