package cn.fanzy.breeze.web.redis.lock.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Slf4j
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "breeze.lock", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeLockConfiguration {

    /**
     * 配置检查
     */
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <分布式锁> 相关的配置。");
    }

}