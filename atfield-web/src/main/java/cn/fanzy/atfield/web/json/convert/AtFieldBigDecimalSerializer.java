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
public class AtFieldBigDecimalSerializer extends JsonSerializer<BigDecimal> {
    private final JsonProperty properties;

    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            JsonProperty.Convert convert = properties.getConvert();
            if (convert != null && convert.getScale() != null && convert.getRoundingMode() != null) {
                value = value.setScale(convert.getScale(), convert.getRoundingMode());
                if(convert.isNumberToString()){
                    jsonGenerator.writeString(value.toString());
                }else{
                    jsonGenerator.writeNumber(value);
                }
            }else {
                jsonGenerator.writeNumber(value);
            }
        }else {
            jsonGenerator.writeNull();
        }
    }
}
