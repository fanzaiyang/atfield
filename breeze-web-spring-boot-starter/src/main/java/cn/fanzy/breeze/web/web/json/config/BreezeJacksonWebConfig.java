package cn.fanzy.breeze.web.web.json.config;

import cn.fanzy.breeze.web.web.json.jackson.BreezeJacksonHttpMessageConverter;
import cn.fanzy.breeze.web.web.json.properties.BreezeWebJsonProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <pre>
 *     返回前端的JSON NULL处理配置
 *     1. 字符串null->""
 *     2. 集合null->[]
 *     3. Boolean null ->false
 *     4. 数字null->0
 *     5. 实体对象null->{}
 * </pre>
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({BreezeWebJsonProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.json", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeJacksonWebConfig {

    @Bean
    public HttpMessageConverters jacksonHttpMessageConverters() {
        return new HttpMessageConverters(new BreezeJacksonHttpMessageConverter());
    }
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <JSON转换器> 相关的配置。");
    }
}
