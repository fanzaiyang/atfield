package cn.fanzy.field.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 *
 * @author fanzaiyang
 * @since 2022-10-31
 */
@Slf4j
public class ExceptionUtil {

    /**
     * 得到异常发生的类和行号，去前3。
     *
     * @param stackTrace 堆栈跟踪
     * @return {@link String}
     */
    public static String getErrorLineNumber3(StackTraceElement[] stackTrace) {
        StringBuilder errMsg = new StringBuilder();
        if (stackTrace == null || stackTrace.length == 0) {
            return errMsg.toString();
        }
        errMsg.append("异常位置：");
        int limit = Math.min(stackTrace.length, 3);
        for (int i = 0; i < limit; i++) {
            StackTraceElement trace = stackTrace[i];
            String format = StrUtil.format("{}.{}:{};", trace.getClassName(), trace.getMethodName(), trace.getLineNumber());
            errMsg.append(format);
        }
        return errMsg.toString();
    }

    public static String getErrorStackMessage(Exception e) {
        return getErrorStackMessage(e, null);
    }

    public static String getErrorStackMessage(Exception e, Integer size) {
        if (e == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String string = sw.getBuffer().toString();
        if (size == null) {
            return string;
        }
        return string.length() > size ? string.substring(0, size) : string;
    }

    public static String getErrorStackMessage(Throwable e) {
        return getErrorStackMessage(e, null);
    }

    public static String getErrorStackMessage(Throwable e, Integer size) {
        if (e == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String string = sw.getBuffer().toString();
        if (size == null || size == -1) {
            return string;
        }
        if (size == 0) {
            return "";
        }
        return string.length() > size ? string.substring(0, size) : string;
    }
}
