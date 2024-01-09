package cn.fanzy.field.web.json.convert;

import cn.fanzy.field.core.spring.SpringUtils;
import cn.fanzy.field.web.json.property.JsonProperty;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

import java.io.Serial;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * null值序列化程序提供程序
 *
 * @author fanzaiyang
 * @date 2023/12/07
 */
public class NullValueSerializerProvider extends DefaultSerializerProvider {
    @Serial
    private static final long serialVersionUID = 3768168172924274362L;

    public NullValueSerializerProvider() {
        super();
    }

    protected NullValueSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }

    @Override
    public DefaultSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new NullValueSerializerProvider(this, config, jsf);
    }

    @Override
    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
        JsonProperty jsonProperty = SpringUtils.getBean(JsonProperty.class);
        if (isStringType(property)) {
            return new FrameworkNullJsonSerializer.NullStringJsonSerializer(jsonProperty.getConvert().getString());
        }
        if (isArrayType(property)) {
            return new FrameworkNullJsonSerializer.NullArrayJsonSerializer(jsonProperty.getConvert().getArray());
        }
        if (isNumberType(property)) {
            return new FrameworkNullJsonSerializer.NullNumberJsonSerializer(jsonProperty.getConvert().getNumber());
        }
        if (isBooleanType(property)) {
            return new FrameworkNullJsonSerializer.NullBooleanJsonSerializer(jsonProperty.getConvert().getBool());
        }
        if (isDateType(property)) {
            return new FrameworkNullJsonSerializer.NullDateJsonSerializer(jsonProperty.getConvert().getDate());
        }

        if (isMapType(property) || isObjectType(property)) {
            return new FrameworkNullJsonSerializer.NullObjectJsonSerializer();
        }
        return super.findNullValueSerializer(property);
    }

    /**
     * 是否是数组
     */
    private boolean isArrayType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 是否是String
     */
    private boolean isStringType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz);
    }

    private boolean isNumberType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return Number.class.isAssignableFrom(clazz);
    }

    private boolean isBooleanType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return Boolean.class.isAssignableFrom(clazz);
    }

    private boolean isDateType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return Date.class.isAssignableFrom(clazz)
                || java.sql.Date.class.isAssignableFrom(clazz)
                || java.sql.Timestamp.class.isAssignableFrom(clazz)
                || java.sql.Time.class.isAssignableFrom(clazz)
                || java.time.LocalDate.class.isAssignableFrom(clazz)
                || java.time.LocalDateTime.class.isAssignableFrom(clazz)
                || java.time.LocalTime.class.isAssignableFrom(clazz)
                || java.time.ZonedDateTime.class.isAssignableFrom(clazz)
                || java.time.OffsetDateTime.class.isAssignableFrom(clazz)
                || java.time.OffsetTime.class.isAssignableFrom(clazz)
                || java.time.Year.class.isAssignableFrom(clazz)
                || java.time.YearMonth.class.isAssignableFrom(clazz)
                || java.time.MonthDay.class.isAssignableFrom(clazz);
    }

    private boolean isMapType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return Map.class.isAssignableFrom(clazz)
                || Set.class.isAssignableFrom(clazz)
                || JSONObject.class.isAssignableFrom(clazz)
                || Map.Entry.class.isAssignableFrom(clazz);
    }

    private boolean isObjectType(BeanProperty property) {
        Class<?> clazz = property.getType().getRawClass();
        return Object.class.isAssignableFrom(clazz);
    }
}
