package cn.fanzy.atfield.web.configuration;

import cn.fanzy.atfield.web.json.convert.DateJackson2ObjectMapperBuilderCustomizer;
import cn.fanzy.atfield.web.json.convert.DecorateJackson2ObjectMapperBuilder;
import cn.fanzy.atfield.web.json.property.JsonProperty;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * <pre>
 *     返回前端的JSON NULL处理配置
 *     1. 字符串null转为""
 *     2. 集合null转为[]
 *     3. 布尔null 转为false
 *     4. 数字null转为0
 *     5. 实体null转json对象
 * </pre>
 *
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@EnableConfigurationProperties({JsonProperty.class})
@ConditionalOnProperty(prefix = "atfield.web.json.convert", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class JsonConvertAutoConfiguration implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final JacksonProperties jacksonProperties;
    private final WebMvcProperties webMvcProperties;
    public final JsonProperty jsonProperty;

    @Bean
    public Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder(List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
        // 用自定义的DecorateJackson2ObjectMapperBuilder替换默认的Jackson2ObjectMapperBuilder
        Jackson2ObjectMapperBuilder builder = new DecorateJackson2ObjectMapperBuilder();
        builder.applicationContext(this.applicationContext);
        for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
        return builder;
    }

    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new DateJackson2ObjectMapperBuilderCustomizer(jacksonProperties, webMvcProperties);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("开启 <JSON转换器> 相关的配置。");
    }
}
