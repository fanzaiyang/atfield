package cn.fanzy.atfield.tlog.core.enhance.logback.async;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.fanzy.atfield.tlog.core.context.AspectLogContext;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;

/**
 * Logback的异步日志增强appender
 *
 * @author Bryan.Zhang
 * @since 1.1.1
 */
public class AspectLogbackAsyncAppender extends AsyncAppender {

    private Field field;

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (eventObject instanceof LoggingEvent loggingEvent) {

            String resultLog;
            final String logValue = AspectLogContext.getLogValue();

            if (!TLogContext.hasTLogMDC() && StrUtil.isNotBlank(logValue)) {
                if (!loggingEvent.getFormattedMessage().contains(logValue)) {
                    resultLog = StrUtil.format("{} {}", logValue, loggingEvent.getFormattedMessage());

                    if (field == null) {
                        field = ReflectUtil.getField(LoggingEvent.class, "formattedMessage");
                        field.setAccessible(true);
                    }

                    try {
                        field.set(loggingEvent, resultLog);
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            eventObject = loggingEvent;
        }
        super.append(eventObject);
    }
}
