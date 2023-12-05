package cn.fanzy.infra.log.core.enhance.log4j.async;

import cn.fanzy.infra.log.common.context.TLogContext;
import cn.fanzy.infra.log.core.context.AspectLogContext;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.log4j.AsyncAppender;
import org.apache.log4j.spi.LoggingEvent;

import java.lang.reflect.Field;

/**
 * 基于日志适配方式的Log4j的异步日志增强appender
 *
 * @author Bryan.Zhang
 * @since 1.1.1
 */
public class AspectLog4jAsyncAppender extends AsyncAppender {

    private Field field;

    @Override
    public void doAppend(LoggingEvent event) {
        String resultLog;
        if (!TLogContext.hasTLogMDC()
                && StrUtil.isNotBlank(AspectLogContext.getLogValue())) {
            resultLog = StrUtil.format("{} {}", event.getMessage(), AspectLogContext.getLogValue());
        } else {
            resultLog = (String) event.getMessage();
        }

        if (field == null) {
            field = ReflectUtil.getField(LoggingEvent.class, "renderedMessage");
            field.setAccessible(true);
        }

        try {
            field.set(event, resultLog);
        } catch (IllegalAccessException e) {
        }

        super.doAppend(event);
    }
}
