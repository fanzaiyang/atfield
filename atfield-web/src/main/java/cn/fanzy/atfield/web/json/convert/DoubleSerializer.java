package cn.fanzy.atfield.web.json.convert;

import cn.fanzy.atfield.web.json.property.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * BigDecimal序列化器
 *
 * @author fanzaiyang
 * @date 2024/08/16
 */
@RequiredArgsConstructor
public class DoubleSerializer extends JsonSerializer<Double> {
    private final JsonProperty properties;

    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            JsonProperty.Convert convert = properties.getConvert();
            if (convert != null && convert.getScale() != null && convert.getRoundingMode() != null) {
                BigDecimal newValue = BigDecimal.valueOf(value).setScale(convert.getScale(), convert.getRoundingMode());
                if (convert.isNumberToString()) {
                    jsonGenerator.writeString(newValue.toString());
                } else {
                    jsonGenerator.writeNumber(newValue);
                }
            } else {
                jsonGenerator.writeNumber(value);
            }

        } else {
            jsonGenerator.writeNull();
        }
    }
}
