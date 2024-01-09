/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package cn.fanzy.field.tlog.core.enhance.log4j2;

import cn.fanzy.field.tlog.common.constant.TLogConstants;
import cn.fanzy.field.tlog.common.context.TLogContext;
import cn.fanzy.field.tlog.core.context.AspectLogContext;
import cn.hutool.core.util.StrUtil;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MultiformatMessage;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilderFormattable;

import java.util.Locale;

/**
 * log4j2的日志适配器，log4j2会自动加载插件，不需要额外配置
 *
 * @author Bryan.Zhang
 * @since 1.0.0
 */
@Plugin(name = "AspectLogLog4j2Converter", category = PatternConverter.CATEGORY)
@ConverterKeys({"m", "msg", "message", "tm", "tmsg", "tmessage", "tl"})
@PerformanceSensitive("allocation")
public final class AspectLogLog4j2Converter extends LogEventPatternConverter {

    private static final String NOLOOKUPS = "nolookups";

    private final String[] formats;
    private final Configuration config;
    private final TextRenderer textRenderer;
    private final boolean noLookups;

    /**
     * Private constructor.
     *
     * @param options options, may be null.
     */
    private AspectLogLog4j2Converter(final Configuration config, final String[] options) {
        super("Message", "message");
        this.formats = options;
        this.config = config;
        final int noLookupsIdx = loadNoLookups(options);
        this.noLookups = noLookupsIdx >= 0;
        this.textRenderer = loadMessageRenderer(noLookupsIdx >= 0 ? ArrayUtils.remove(options, noLookupsIdx) : options);
    }

    private int loadNoLookups(final String[] options) {
        if (options != null) {
            for (int i = 0; i < options.length; i++) {
                final String option = options[i];
                if (NOLOOKUPS.equalsIgnoreCase(option)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private TextRenderer loadMessageRenderer(final String[] options) {
        if (options != null) {
            for (final String option : options) {
                switch (option.toUpperCase(Locale.ROOT)) {
                    case "ANSI":
                        if (Loader.isJansiAvailable()) {
                            return new HtmlTextRenderer(options);
                        }
                        StatusLogger.getLogger()
                                .warn("You requested ANSI message rendering but JANSI is not on the classpath.");
                        return null;
                    case "HTML":
                        return new HtmlTextRenderer(options);
                }
            }
        }
        return null;
    }

    /**
     * Obtains an instance of pattern converter.
     *
     * @param config  The Configuration.
     * @param options options, may be null.
     * @return instance of pattern converter.
     */
    public static AspectLogLog4j2Converter newInstance(final Configuration config, final String[] options) {
        return new AspectLogLog4j2Converter(config, options);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void format(final LogEvent event, final StringBuilder toAppendTo) {
        String logLable = event.getContextData().getValue(TLogConstants.MDC_KEY);

        if (StrUtil.isBlank(logLable)){
            logLable = AspectLogContext.getLogValue();
        }

        if (!TLogContext.hasTLogMDC()) {
            if (StrUtil.isNotBlank(logLable)) {
                toAppendTo.append(StrUtil.format("{} ", logLable));
            }
        }
        final Message msg = event.getMessage();
        if (msg instanceof StringBuilderFormattable) {

            final boolean doRender = textRenderer != null;
            final StringBuilder workingBuilder = doRender ? new StringBuilder(80) : toAppendTo;

            final StringBuilderFormattable stringBuilderFormattable = (StringBuilderFormattable) msg;
            final int offset = workingBuilder.length();
            stringBuilderFormattable.formatTo(workingBuilder);

            // TODO can we optimize this?
            if (config != null && !noLookups) {
                for (int i = offset; i < workingBuilder.length() - 1; i++) {
                    if (workingBuilder.charAt(i) == '$' && workingBuilder.charAt(i + 1) == '{') {
                        final String value = workingBuilder.substring(offset, workingBuilder.length());
                        workingBuilder.setLength(offset);
                        workingBuilder.append(config.getStrSubstitutor().replace(event, value));
                    }
                }
            }
            if (doRender) {
                textRenderer.render(workingBuilder, toAppendTo);
            }
            return;
        }
        if (msg != null) {
            String result;
            if (msg instanceof MultiformatMessage) {
                result = ((MultiformatMessage) msg).getFormattedMessage(formats);
            } else {
                result = msg.getFormattedMessage();
            }
            if (result != null) {
                toAppendTo.append(config != null && result.contains("${")
                        ? config.getStrSubstitutor().replace(event, result) : result);
            } else {
                toAppendTo.append("null");
            }
        }
    }
}
