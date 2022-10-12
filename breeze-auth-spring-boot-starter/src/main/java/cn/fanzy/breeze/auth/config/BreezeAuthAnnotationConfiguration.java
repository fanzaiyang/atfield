package cn.fanzy.breeze.auth.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouteFunction;
import cn.fanzy.breeze.auth.function.BreezeDefaultRouteFunction;
import cn.fanzy.breeze.auth.properties.BreezeAuthProperties;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(BreezeAuthProperties.class)
@ConditionalOnProperty(prefix = "breeze.auth.annotation", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeAuthAnnotationConfiguration implements WebMvcConfigurer {

    private final BreezeAuthProperties properties;

    public static final List<String> SWAGGER_LIST = CollUtil.toList("/doc.html", "/swagger-resources/**", "/webjars/**", "/favicon.ico", "/error");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns(properties.getAnnotation().getPathPatterns())
                .excludePathPatterns(properties.getAnnotation().getExcludePathPatterns());
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void checkConfig() {
        List<String> list = properties.getAnnotation().getExcludePathPatterns();
        list.addAll(SWAGGER_LIST);
        properties.getAnnotation().setExcludePathPatterns(list);
        log.info("「微风组件」开启 <注册SaToken注解拦截器> 相关的配置。白名单：{}", JSONUtil.toJsonStr(list));
    }

}