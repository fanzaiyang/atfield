package cn.fanzy.breeze.web.ip.config;

import cn.fanzy.breeze.web.ip.interceptor.BreezeIpGlobalCheckInterceptor;
import cn.fanzy.breeze.web.ip.properties.BreezeIpProperties;
import cn.fanzy.breeze.web.ip.service.BreezeIpGlobalCheckService;
import cn.fanzy.breeze.web.ip.service.impl.BreezeIpDefaultGlobalCheckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeIpProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.ip", name = {"enable"}, havingValue = "true")
public class BreezeIpGlobalCheckConfiguration implements WebMvcConfigurer {

    private final BreezeIpProperties breezeIpProperties;

    @Bean
    @ConditionalOnMissingBean
    public BreezeIpGlobalCheckService breezeIpGlobalCheckService() {
        return new BreezeIpDefaultGlobalCheckService(breezeIpProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("「微风组件」开启全局IP拦截");
        String[] path = breezeIpProperties.getPathPatterns();
        if (path == null || path.length == 0) {
            path = new String[]{"/**"};
        }
        registry.addInterceptor(new BreezeIpGlobalCheckInterceptor(breezeIpGlobalCheckService()))
                .addPathPatterns(path);
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
    public void init() {
        log.info("「微风组件」开启<IP全局校验>相关的配置。");
    }
}
