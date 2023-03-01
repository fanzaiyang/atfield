package cn.fanzy.breeze.web.web.json.jackson;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
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
 * 微风null值序列化器
 * 这个有问题，已废弃
 * @author fanzaiyang
 * @date 2023-03-01
 */
@Deprecated
public class BreezeNullValueSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String fieldName = gen.getOutputContext().getCurrentName();
        //反射获取字段
        Object currentValue = gen.getCurrentValue();
        Field field = ReflectUtil.getField(currentValue.getClass(), fieldName);
        if (ObjectUtil.isNotNull(field)){
            // 数字类型Integer、Double、Long等返回null
            if (Number.class.isAssignableFrom(field.getType())){
                gen.writeNull();
                return;
            }
            //String类型返回""
            if (Objects.equals(field.getType(), String.class)) {
                gen.writeString("");
                return;
            }
            //List类型返回[]
            if (Collection.class.isAssignableFrom(field.getType())||Objects.equals(field.getType(), List.class)) {
                gen.writeStartArray();
                gen.writeEndArray();
                return;
            }
            // Date => null
            if(Objects.equals(field.getType(), Date.class)||Objects.equals(field.getType(), java.sql.Date.class)||
                    Objects.equals(field.getType(), LocalDate.class)){
                gen.writeNull();
                return;
            }
            // Boolean =>false
            if(Objects.equals(field.getType(),Boolean.class)){
                gen.writeBoolean(false);
                return;
            }
            //其他Object默认返回{}
            gen.writeStartObject();
            gen.writeEndObject();
        }
    }
}
