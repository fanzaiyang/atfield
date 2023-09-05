package cn.fanzy.breeze.web.web.json.jackson;

import cn.fanzy.breeze.core.utils.NumberUtil;
import cn.fanzy.breeze.web.web.json.properties.BreezeWebJsonProperties;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

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
            }else {
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
            }else {
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
            }else {
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
            }else {
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
            }else {
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

}
