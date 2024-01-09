package cn.fanzy.field.tlog.support;

import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.fanzy.field.tlog.common.context.TLogContext;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.AbstractJsonProvider;

import java.io.IOException;

public class TLogLogstashLogbackProvider extends AbstractJsonProvider<ILoggingEvent> {

    static {
        TLogContext.setHasLogstash(true);
    }

    @Override
    public void writeTo(JsonGenerator jsonGenerator, ILoggingEvent event) throws IOException {
        event.getMDCPropertyMap().forEach((key, value) -> {
            try {
                jsonGenerator.writeFieldName(key);
                jsonGenerator.writeString(value);
            }catch (Exception ignored){}
        });
    }
}
