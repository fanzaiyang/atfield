package cn.fanzy.atfield.web.json.jackson;

import cn.fanzy.atfield.web.json.property.JsonProperty;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author fanzaiyang
 * @date 2023/09/05
 */
public class CustomizeNullJsonSerializer {
    /**
     * 处理数组集合类型的null值
     */
    public static class NullArrayJsonSerializer extends JsonSerializer<Object> {

        private final JsonProperty.Convert.ArrayCovert arrayConfig;

        public NullArrayJsonSerializer(JsonProperty.Convert.ArrayCovert convert) {
            this.arrayConfig = convert;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (arrayConfig.isEnable()) {
                jsonGenerator.writeStartArray();
                jsonGenerator.writeEndArray();
            } else {
                jsonGenerator.writeNull();
            }
        }
    }

    /**
     * 处理字符串类型的null值
     */
    public static class NullStringJsonSerializer extends JsonSerializer<Object> {
        private final JsonProperty.Convert.StringCovert config;

        public NullStringJsonSerializer(JsonProperty.Convert.StringCovert config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.isEnable()) {
                if (config.getValue() == null ||
                        StrUtil.equalsIgnoreCase(config.getValue(), "null")) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeString(config.getValue());
                }
            } else {
                jsonGenerator.writeNull();
            }
        }
    }

    /**
     * 处理数值类型的null值
     */
    public static class NullNumberJsonSerializer extends JsonSerializer<Object> {
        private final JsonProperty.Convert.NumberCovert config;

        public NullNumberJsonSerializer(JsonProperty.Convert.NumberCovert config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.isEnable()) {
                if (config.getValue() == null) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeNumber(config.getValue());
                }
            } else {
                jsonGenerator.writeNull();
            }

        }
    }

    public static class NullDateJsonSerializer extends JsonSerializer<Object> {
        private final JsonProperty.Convert.DateCovert config;

        public NullDateJsonSerializer(JsonProperty.Convert.DateCovert config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.isEnable()) {
                if (config.getValue() == null ||
                        StrUtil.equalsIgnoreCase(config.getValue(), "null")) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeString(config.getValue());
                }
            } else {
                jsonGenerator.writeNull();
            }
        }
    }

    /**
     * 处理boolean类型的null值
     */
    public static class NullBooleanJsonSerializer extends JsonSerializer<Object> {
        private final JsonProperty.Convert.BooleanCovert config;

        public NullBooleanJsonSerializer(JsonProperty.Convert.BooleanCovert config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.isEnable()) {
                if (config.getValue() == null) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeBoolean(config.getValue());
                }
            } else {
                jsonGenerator.writeNull();
            }
        }
    }

    /**
     * 处理实体对象类型的null值
     */
    public static class NullObjectJsonSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeEndObject();
        }
    }

    public static class NullAnyJsonSerializer extends JsonSerializer<Object> {
        private final JsonProperty config;

        public NullAnyJsonSerializer(JsonProperty config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            String fieldName = jsonGenerator.getOutputContext().getCurrentName();
            Object currentValue = jsonGenerator.getCurrentValue();
            Field field = ReflectUtil.getField(currentValue.getClass(), fieldName);
            if (ObjectUtil.isNull(field)) {
                // 未知的类型，写NULL
                jsonGenerator.writeNull();
                return;
            }
            // 数字类型Integer、Double、Long等返回null
            if (Number.class.isAssignableFrom(field.getType())) {
                new NullNumberJsonSerializer(config.getConvert().getNumber())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            //String类型返回""
            if (Objects.equals(field.getType(), String.class)) {
                new NullStringJsonSerializer(config.getConvert().getString())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            //List类型返回[]
            if (Collection.class.isAssignableFrom(field.getType()) || Objects.equals(field.getType(), List.class)) {
                new NullArrayJsonSerializer(config.getConvert().getArray())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            // Date => null
            if (Objects.equals(field.getType(), Date.class) || Objects.equals(field.getType(), java.sql.Date.class) ||
                    Objects.equals(field.getType(), LocalDate.class)) {
                new NullDateJsonSerializer(config.getConvert().getDate())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            // Boolean =>false
            if (Objects.equals(field.getType(), Boolean.class)) {
                new NullBooleanJsonSerializer(config.getConvert().getBool())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            //其他Object默认返回{}
            new NullObjectJsonSerializer()
                    .serialize(value, jsonGenerator, serializerProvider);
        }
    }
}
