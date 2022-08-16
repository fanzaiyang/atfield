package cn.fanzy.breeze.web.model.utils;

import cn.fanzy.breeze.web.model.properties.BreezeModelProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties({BreezeModelProperties.class})
public class BreezeModelContext {
    public static BreezeModelProperties properties;

    private BreezeModelProperties breezeModelProperties;

    @PostConstruct
    public void init() {
        properties = breezeModelProperties;
    }
}
