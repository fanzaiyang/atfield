package cn.fanzy.breeze.web.web.json.config;

import cn.fanzy.breeze.web.web.json.jackson.BreezeBeanSerializerModifier;
import cn.fanzy.breeze.web.web.json.jackson.BreezeNullValueSerializer;
import cn.fanzy.breeze.web.web.json.properties.BreezeWebJsonProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

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
@AutoConfigureAfter(JacksonAutoConfiguration.class)
@EnableConfigurationProperties({BreezeWebJsonProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.json", name = {"enable"}, havingValue = "true",matchIfMissing = true)
public class BreezeJacksonWebConfig {

    @Value("${spring.mvc.format.date-time:yyyy-MM-dd HH:mm:ss}")
    private String dateTimeFormat;

    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        log.info("mappingJackson2HttpMessageConverter....");
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        //现在需要在中间加一个
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
        /** 为objectMapper注册一个带有SerializerModifier的Factory */
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(new BreezeBeanSerializerModifier()));
        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
        serializerProvider.setNullValueSerializer(new BreezeNullValueSerializer());

        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);  // 设置objectMapper
        return mappingJackson2HttpMessageConverter;
    }
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <JSON转换器> 相关的配置。");
    }
}
