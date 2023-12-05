package cn.fanzy.breeze.web.web.json.jackson;

import cn.fanzy.breeze.web.web.json.properties.BreezeWebJsonProperties;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

/**
 * @author fanzaiyang
 * @date 2023/09/05
 */
public class BreezeCustomizeNullJsonSerializer {
    /**
     * 处理数组集合类型的null值
     */
    public static class NullArrayJsonSerializer extends JsonSerializer<Object> {

        private final BreezeWebJsonProperties.ArrayConfig arrayConfig;

        public NullArrayJsonSerializer(BreezeWebJsonProperties.ArrayConfig arrayConfig) {
            this.arrayConfig = arrayConfig;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (arrayConfig.getEnable()) {
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
        private final BreezeWebJsonProperties.StringConfig config;

        public NullStringJsonSerializer(BreezeWebJsonProperties.StringConfig config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.getEnable()) {
                if (config.getDefaultValue() == null ||
                        StrUtil.equalsIgnoreCase(config.getDefaultValue(), "null")) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeString(config.getDefaultValue());
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
        private final BreezeWebJsonProperties.NumberConfig config;

        public NullNumberJsonSerializer(BreezeWebJsonProperties.NumberConfig config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.getEnable()) {
                if (config.getDefaultValue() == null) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeNumber(config.getDefaultValue());
                }
            } else {
                jsonGenerator.writeNull();
            }

        }
    }

    public static class NullDateJsonSerializer extends JsonSerializer<Object> {
        private final BreezeWebJsonProperties.DateConfig config;

        public NullDateJsonSerializer(BreezeWebJsonProperties.DateConfig config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.getEnable()) {
                if (config.getDefaultValue() == null ||
                        StrUtil.equalsIgnoreCase(config.getDefaultValue(), "null")) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeString(config.getDefaultValue());
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
        private final BreezeWebJsonProperties.BooleanConfig config;

        public NullBooleanJsonSerializer(BreezeWebJsonProperties.BooleanConfig config) {
            this.config = config;
        }

        @Override
        public void serialize(Object value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            if (config.getEnable()) {
                if (config.getDefaultValue() == null) {
                    jsonGenerator.writeNull();
                } else {
                    jsonGenerator.writeBoolean(config.getDefaultValue());
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
        private final BreezeWebJsonProperties config;

        public NullAnyJsonSerializer(BreezeWebJsonProperties config) {
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
                new NullNumberJsonSerializer(config.getNumber())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            //String类型返回""
            if (Objects.equals(field.getType(), String.class)) {
                new NullStringJsonSerializer(config.getString())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            //List类型返回[]
            if (Collection.class.isAssignableFrom(field.getType()) || Objects.equals(field.getType(), List.class)) {
                new NullArrayJsonSerializer(config.getArray())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            // Date => null
            if (Objects.equals(field.getType(), Date.class) || Objects.equals(field.getType(), java.sql.Date.class) ||
                    Objects.equals(field.getType(), LocalDate.class)) {
                new NullDateJsonSerializer(config.getDate())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            // Boolean =>false
            if (Objects.equals(field.getType(), Boolean.class)) {
                new NullBooleanJsonSerializer(config.getBool())
                        .serialize(value, jsonGenerator, serializerProvider);
                return;
            }
            //其他Object默认返回{}
            new NullObjectJsonSerializer()
                    .serialize(value, jsonGenerator, serializerProvider);
        }
    }
}
