package cn.fanzy.atfield.ipsec.configuration;

import cn.fanzy.atfield.ipsec.service.IpCheckService;
import cn.fanzy.atfield.ipsec.service.impl.IpCheckServiceImpl;
import cn.fanzy.atfield.ipsec.service.impl.IpStorageServiceImpl;
import cn.fanzy.atfield.ipsec.advice.IpCheckAdvice;
import cn.fanzy.atfield.ipsec.interceptor.IpCheckInterceptor;
import cn.fanzy.atfield.ipsec.property.IpProperty;
import cn.fanzy.atfield.ipsec.service.IpStorageService;
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
        return new IpCheckServiceImpl();
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
        registry.addInterceptor(new IpCheckInterceptor(ipCheckService(), ipStorageService()))
                .addPathPatterns(patterns)//拦截所有的路径
                .excludePathPatterns(property.getGlobal().getExcludePathPatterns());
    }

    @PostConstruct
    public void checkConfig() {
        log.info("开启 <IP检查> 相关的配置");
    }
}
