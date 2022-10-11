package cn.fanzy.breeze.web.utils;

import cn.hutool.core.util.StrUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

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
            StackTraceElement trace = stackTrace[0];
            String format = StrUtil.format("{}.{}:{};", trace.getClassName(), trace.getMethodName(), trace.getLineNumber());
            errMsg.append(format);
        }
        return errMsg.toString();
    }
    public static String getErrorStackMessage(Exception e) {
        if (e == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
    public static String getErrorStackMessage(Throwable e) {
        if (e == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
