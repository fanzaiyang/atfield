package cn.fanzy.atfield.web.configuration;

import cn.fanzy.atfield.web.advice.ResponseWrapperAdvice;
import cn.fanzy.atfield.web.exception.GlobalExceptionAdvice;
import cn.fanzy.atfield.web.exception.GlobalExceptionErrorAdvice;
import cn.fanzy.atfield.web.exception.SaTokenExceptionAdvice;
import cn.fanzy.atfield.web.exception.SaTokenExceptionErrorAdvice;
import cn.fanzy.atfield.web.filter.ReplaceStreamFilter;
import cn.fanzy.atfield.web.json.property.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({JsonProperty.class})
@ImportAutoConfiguration({
        ReplaceStreamFilter.class,
        GlobalExceptionAdvice.class,
        ResponseWrapperAdvice.class,
        SaTokenExceptionAdvice.class,
        GlobalExceptionErrorAdvice.class,
        SaTokenExceptionErrorAdvice.class})
@PropertySource(
        name = "TLog Default framework Properties",
        value = "classpath:/META-INF/infra-web-default.properties")
public class CustomWebAutoConfiguration implements WebMvcConfigurer {
    /**
     * 添加资源处理程序
     * 跨域配置会覆盖默认的配置，
     * 因此需要实现addResourceHandlers方法，增加默认配置静态路径
     *
     * @param registry 注册表
     */
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
}
