package cn.fanzy.atfield.web.json.convert;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期 Jackson2 对象映射器生成器定制器
 *
 * @author fanzaiyang
 * @date 2023/12/15
 */
@RequiredArgsConstructor
public class DateJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {
    private final JacksonProperties jacksonProperties;
    private final WebMvcProperties webMvcProperties;

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {

        String format = StrUtil.blankToDefault(jacksonProperties.getDateFormat(), webMvcProperties.getFormat().getDateTime());
        String dateTimeFormat = StrUtil.blankToDefault(format, "yyyy-MM-dd HH:mm:ss");
        String dateFormat = StrUtil.blankToDefault(webMvcProperties.getFormat().getDate(), "yyyy-MM-dd");
        String timeFormat = StrUtil.blankToDefault(webMvcProperties.getFormat().getTime(), "HH:mm:ss");
        // 设置java.util.Date时间类的序列化以及反序列化的格式
        builder.simpleDateFormat(dateTimeFormat);
        if (jacksonProperties.getLocale() == null) {
            builder.locale(Locale.CHINA);
        } else {
            builder.locale(jacksonProperties.getLocale());
        }
        if (jacksonProperties.getTimeZone() != null) {
            builder.timeZone(jacksonProperties.getTimeZone());
        } else {
            builder.timeZone("GTM+8");
        }
        // JSR 310日期时间处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        builder.modules(javaTimeModule);

        // 全局转化Long类型为String，解决序列化后传入前端Long类型精度丢失问题
        builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        builder.serializerByType(Long.class, ToStringSerializer.instance);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
