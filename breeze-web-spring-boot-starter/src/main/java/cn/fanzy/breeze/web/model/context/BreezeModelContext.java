package cn.fanzy.breeze.web.model.context;

import cn.fanzy.breeze.web.model.properties.BreezeModelProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * @author fanzaiyang
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties({BreezeModelProperties.class})
@PropertySource(value = {"classpath:application-web.properties"})
public class BreezeModelContext {
    public static BreezeModelProperties properties;

    private final BreezeModelProperties breezeModelProperties;

    @PostConstruct
    public void init() {
        properties = breezeModelProperties;
        log.info("「微风组件」开启 <Json响应> 相关的配置。");
    }
}
