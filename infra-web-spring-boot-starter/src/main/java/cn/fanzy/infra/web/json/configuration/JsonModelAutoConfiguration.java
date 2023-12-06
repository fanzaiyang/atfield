package cn.fanzy.infra.web.json.configuration;

import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.web.json.property.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * json模型自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(JsonProperty.class)
public class JsonModelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JsonProperty infraJsonProperty() {
        return new JsonProperty();
    }

    public static JsonProperty getJsonProperty() {
        return SpringUtils.getBean(JsonProperty.class);
    }
}
