package cn.fanzy.atfield.tlog.core.enhance.log4j;

import cn.fanzy.atfield.tlog.core.context.AspectLogContext;
import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.hutool.core.util.StrUtil;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 基于日志适配方式的log4j的自定义PatternConvert
 *
 * @author Bryan.Zhang
 * @since 1.0.0
 */
public class AspectLog4jPatternConverter extends PatternConverter {
    @Override
    protected String convert(LoggingEvent loggingEvent) {
       //只有在MDC没有设置的情况下才加到message里
       String renderedMessage = loggingEvent.getRenderedMessage();
        String logValue = AspectLogContext.getLogValue();
        if (StrUtil.isNotBlank(logValue) && !TLogContext.hasTLogMDC()) {
            return logValue + " " + renderedMessage;
        }
        return renderedMessage;
    }
}
