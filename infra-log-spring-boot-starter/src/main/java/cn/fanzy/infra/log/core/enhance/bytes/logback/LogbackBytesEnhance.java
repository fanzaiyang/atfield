package cn.fanzy.infra.log.core.enhance.bytes.logback;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import cn.fanzy.infra.log.core.context.AspectLogContext;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Marker;

/**
 * Logback的字节增强类
 *
 * @author Bryan.Zhang
 * @since 1.1.3
 */
public class LogbackBytesEnhance {

    public static void enhance(final String localFQCN, final Marker marker, final Level level, final String msg, final Object[] params,
                               final Throwable t, Logger thisObj) {
        String resultLog;
        if (StrUtil.isNotBlank(AspectLogContext.getLogValue())) {
            resultLog = StrUtil.format("{} {}", AspectLogContext.getLogValue(), msg);
        } else {
            resultLog = msg;
        }

        LoggingEvent le = new LoggingEvent(localFQCN, thisObj, level, resultLog, t, params);
        // 有setMarker改为addMarker
        le.addMarker(marker);
        thisObj.callAppenders(le);
    }
}
