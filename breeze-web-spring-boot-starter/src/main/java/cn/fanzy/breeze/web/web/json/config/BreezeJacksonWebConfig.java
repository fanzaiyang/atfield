package cn.fanzy.breeze.web.web.json.config;

import cn.fanzy.breeze.web.web.json.properties.BreezeWebJsonProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.PostConstruct;

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
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@EnableConfigurationProperties({BreezeWebJsonProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.json", name = {"enable"}, havingValue = "true",matchIfMissing = true)
public class BreezeJacksonWebConfig {

    @Value("${spring.mvc.format.date-time:yyyy-MM-dd HH:mm:ss}")
    private String dateTimeFormat;
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        return new BreezeJacksonObjectMapper();
    }

//    @Bean
//    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//        log.info("mappingJackson2HttpMessageConverter....");
//        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = new ObjectMapper();
//        //现在需要在中间加一个
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
//        /** 为objectMapper注册一个带有SerializerModifier的Factory */
//        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
//                .withSerializerModifier(new BreezeBeanSerializerModifier()));
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
//        serializerProvider.setNullValueSerializer(new BreezeNullValueSerializer());
//
//        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);  // 设置objectMapper
//        return mappingJackson2HttpMessageConverter;
//    }
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <JSON转换器> 相关的配置。");
    }
}
