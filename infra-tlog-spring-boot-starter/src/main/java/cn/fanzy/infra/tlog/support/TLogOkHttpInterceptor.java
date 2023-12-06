package cn.fanzy.infra.tlog.support;


import cn.fanzy.infra.tlog.common.constant.TLogConstants;
import cn.fanzy.infra.tlog.common.context.SpanIdGenerator;
import cn.fanzy.infra.tlog.common.context.TLogContext;
import cn.fanzy.infra.tlog.common.spring.TLogSpringAware;
import cn.fanzy.infra.tlog.common.utils.LocalhostUtil;
import cn.hutool.core.util.StrUtil;
import okhttp3.Interceptor;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * OkHttp的拦截器
 * @author Bryan.Zhang
 * @since 1.3.0
 */
public class TLogOkHttpInterceptor implements Interceptor {
    
    private final Logger log = LoggerFactory.getLogger(TLogOkHttpInterceptor.class);
    
    @Override
    public Response intercept(final Chain chain) throws IOException {
        Builder builder = chain.request().newBuilder();
        String traceId = TLogContext.getTraceId();
        if (StrUtil.isNotBlank(traceId)) {
            String appName = TLogSpringAware.getProperty("spring.application.name");
            builder.header(TLogConstants.TLOG_TRACE_KEY, traceId);
            builder.header(TLogConstants.TLOG_SPANID_KEY, SpanIdGenerator.generateNextSpanId());
            builder.header(TLogConstants.PRE_IVK_APP_KEY, appName);
            builder.header(TLogConstants.PRE_IVK_APP_HOST, LocalhostUtil.getHostName());
            builder.header(TLogConstants.PRE_IP_KEY, LocalhostUtil.getHostIp());
        } else {
            log.debug("[TLOG]本地threadLocal变量没有正确传递traceId,本次调用不传递traceId");
        }

        return chain.proceed(builder.build());
    }
}
