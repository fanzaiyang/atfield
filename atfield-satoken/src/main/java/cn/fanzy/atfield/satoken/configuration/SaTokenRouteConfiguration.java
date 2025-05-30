package cn.fanzy.atfield.satoken.configuration;

import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.atfield.satoken.interceptor.StpContextInterceptor;
import cn.fanzy.atfield.satoken.property.SaTokenExtraProperty;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SA 令牌路由配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(SaTokenExtraProperty.class)
@ConditionalOnProperty(prefix = "atfield.sa-token.route", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class SaTokenRouteConfiguration implements WebMvcConfigurer {
    private final SaTokenExtraProperty property;

    @Value("${atfield.web.attach.public-read:false}")
    private Boolean publicRead;
    @Value("${atfield.web.attach.context-path:/attach}")
    private String contextPath;

    @Bean
    @ConditionalOnMissingBean(SaParamFunction.class)
    public SaParamFunction<Object> saParamFunction() {
        return r -> StpUtil.checkLogin();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        List<String> excludePathPatterns = CollUtil.toList(property.getRoute().getExcludePathPatterns());
        if (publicRead && StrUtil.isNotBlank(contextPath)) {
            String pattern = StrUtil.addSuffixIfNot(contextPath, "/") + "**";
            excludePathPatterns.add(pattern);
        }
        registry.addInterceptor(new SaInterceptor(saParamFunction()))
                .addPathPatterns(property.getRoute().getAddPathPatterns())
                .excludePathPatterns(ArrayUtil.toArray(excludePathPatterns, String.class));

        // 注册 StpContext 拦截器，用于在多线程中获取当前登录会话。
        registry.addInterceptor(new StpContextInterceptor())
                .addPathPatterns("/**");
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
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
