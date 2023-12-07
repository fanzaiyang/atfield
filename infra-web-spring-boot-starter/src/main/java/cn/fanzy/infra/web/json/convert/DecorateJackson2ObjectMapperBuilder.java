package cn.fanzy.infra.web.json.convert;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class DecorateJackson2ObjectMapperBuilder extends Jackson2ObjectMapperBuilder {
    @Override
    public void configure(ObjectMapper objectMapper) {
        super.configure(objectMapper);
        /**
         * 给ObjectMapper设置自定义的DefaultSerializerProvider
         * {@link NullValueSerializerProvider}
         */
        objectMapper.setSerializerProvider(new NullValueSerializerProvider());
    }
}
