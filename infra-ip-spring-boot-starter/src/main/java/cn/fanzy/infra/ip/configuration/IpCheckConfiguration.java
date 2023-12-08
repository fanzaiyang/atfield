package cn.fanzy.infra.ip.configuration;

import cn.fanzy.infra.ip.advice.IpCheckAdvice;
import cn.fanzy.infra.ip.interceptor.IpCheckInterceptor;
import cn.fanzy.infra.ip.property.IpProperty;
import cn.fanzy.infra.ip.service.IpCheckService;
import cn.fanzy.infra.ip.service.IpStorageService;
import cn.fanzy.infra.ip.service.impl.IpCheckServiceImpl;
import cn.fanzy.infra.ip.service.impl.IpStorageServiceImpl;
import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ImportAutoConfiguration({IpCheckInterceptor.class, IpCheckAdvice.class})
@AutoConfigureBefore({IpCheckAdvice.class, IpCheckInterceptor.class})
@EnableConfigurationProperties({IpProperty.class})
public class IpCheckConfiguration implements WebMvcConfigurer {
    private final IpProperty property;

    @Bean
    @ConditionalOnMissingBean
    public IpStorageService ipStorageService() {
        return new IpStorageServiceImpl(property);
    }

    @Bean
    @ConditionalOnMissingBean
    public IpCheckService ipCheckService() {
        return new IpCheckServiceImpl(ipStorageService());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (property.getGlobal().getEnable() == null ||
                !property.getGlobal().getEnable()) {
            return;
        }
        String[] patterns = property.getGlobal().getIncludePathPatterns();
        if (patterns.length == 0) {
            patterns = new String[]{"/**"};
        }
        registry.addInterceptor(new IpCheckInterceptor(ipCheckService()))
                .addPathPatterns(patterns)//拦截所有的路径
                .excludePathPatterns(property.getGlobal().getExcludePathPatterns());
    }

    @PostConstruct
    public void checkConfig() {
        log.info("开启 <IP检查> 相关的配置");
    }
}
