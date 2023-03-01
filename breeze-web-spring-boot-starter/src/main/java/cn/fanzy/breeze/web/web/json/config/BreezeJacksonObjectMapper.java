package cn.fanzy.breeze.web.web.json.config;

import cn.fanzy.breeze.web.web.json.jackson.BreezeBeanSerializerModifier;
import cn.fanzy.breeze.web.web.json.jackson.BreezeCustomizeNullJsonSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class BreezeJacksonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 4349248944480408489L;

    public BreezeJacksonObjectMapper() {
        super();
        // 收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig()
                .withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.registerModule(new JavaTimeModule());
        this.setSerializerFactory(this.getSerializerFactory()
                .withSerializerModifier(new BreezeBeanSerializerModifier()));
        this.getSerializerProvider().setNullValueSerializer(new BreezeCustomizeNullJsonSerializer.NullObjectJsonSerializer());
    }
}
