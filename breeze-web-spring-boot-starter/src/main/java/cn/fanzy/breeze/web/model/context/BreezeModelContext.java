package cn.fanzy.breeze.web.model.context;

import cn.fanzy.breeze.web.model.properties.BreezeModelProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeModelProperties.class})
public class BreezeModelContext {
    public static BreezeModelProperties properties;

    private final BreezeModelProperties breezeModelProperties;
    @PostConstruct
    public void init() {
        properties = breezeModelProperties;
        log.info("「微风组件」开启Json返回值配置组件。");
    }
}
