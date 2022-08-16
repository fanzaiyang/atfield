package cn.fanzy.breeze.web.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;


/**
 * Json脱敏序列化
 *
 * @author fanzaiyang
 */
public class BreezeSensitiveSerialize extends JsonSerializer<Object> implements ContextualSerializer {

    private BreezeSensitiveEnum type;

    public BreezeSensitiveSerialize() {
    }

    public BreezeSensitiveSerialize(final BreezeSensitiveEnum type) {
        this.type = type;
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers)
            throws IOException {
        switch (this.type) {
            case ID_CARD:
                jsonGenerator.writeString(BreezeSensitiveUtil.idCard(String.valueOf(value)));
                break;
            case MOBILE_PHONE:
                jsonGenerator.writeString(BreezeSensitiveUtil.phone(String.valueOf(value)));
                break;
            case PASSWORD:
                jsonGenerator.writeString(BreezeSensitiveUtil.password(String.valueOf(value)));
                break;
            case NAME:
                jsonGenerator.writeString(BreezeSensitiveUtil.name(String.valueOf(value)));
                break;
            case BANK_NUMBER:
                jsonGenerator.writeString(BreezeSensitiveUtil.bankNumber(String.valueOf(value)));
                break;
            default:
                jsonGenerator.writeString(String.valueOf(value));

        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty)
            throws JsonMappingException {

        if (beanProperty != null) {

            // 非 String 类直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                BreezeSensitive breezeSensitiveInfo = beanProperty.getAnnotation(BreezeSensitive.class);
                if (breezeSensitiveInfo == null) {
                    breezeSensitiveInfo = beanProperty.getContextAnnotation(BreezeSensitive.class);
                }
                // 如果能得到注解，就将注解的 value 传入 SensitiveInfoSerialize
                if (breezeSensitiveInfo != null) {

                    return new BreezeSensitiveSerialize(breezeSensitiveInfo.value());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);

    }
}
