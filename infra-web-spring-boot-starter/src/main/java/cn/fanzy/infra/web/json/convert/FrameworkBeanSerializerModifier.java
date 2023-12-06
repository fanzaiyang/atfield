package cn.fanzy.infra.web.json.convert;

import cn.fanzy.infra.web.json.property.JsonProperty;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * spiderbean序列化程序修饰符
 *
 * @author fanzaiyang
 * @date 2023/09/05
 */
@Slf4j
@RequiredArgsConstructor
public class FrameworkBeanSerializerModifier extends BeanSerializerModifier {
    private final JsonProperty properties;

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        JsonProperty.Convert convert = properties.getConvert();
        // 循环所有的beanPropertyWriter
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            // 判断字段的类型，如果是数组或集合则注册nullSerializer
            if (isArrayType(writer)) {
                // 给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(new FrameworkNullJsonSerializer
                        .NullArrayJsonSerializer(convert.getArray()));
                continue;
            }
            if (isStringType(writer)) {
                writer.assignNullSerializer(new FrameworkNullJsonSerializer
                        .NullStringJsonSerializer(convert.getString()));
                continue;
            }
            if (isNumberType(writer)) {
                writer.assignNullSerializer(new FrameworkNullJsonSerializer
                        .NullNumberJsonSerializer(convert.getNumber()));
                continue;
            }
            if (isDateType(writer)) {
                writer.assignNullSerializer(new FrameworkNullJsonSerializer
                        .NullDateJsonSerializer(convert.getDate()));
                continue;
            }
            if (isBooleanType(writer)) {
                writer.assignNullSerializer(new FrameworkNullJsonSerializer
                        .NullBooleanJsonSerializer(convert.getBool()));
                continue;
            }
            writer.assignNullSerializer(new FrameworkNullJsonSerializer.NullAnyJsonSerializer(convert));
        }
        return beanProperties;
    }

    /**
     * 是否是数组
     */
    private boolean isArrayType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是String
     */
    private boolean isStringType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是数值类型
     */
    private boolean isNumberType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是boolean
     */
    private boolean isBooleanType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Boolean.class);
    }

    private boolean isDateType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return clazz.equals(Date.class) || clazz.equals(LocalDate.class) || clazz.equals(java.sql.Date.class)
                || clazz.equals(LocalDateTime.class);
    }
}
