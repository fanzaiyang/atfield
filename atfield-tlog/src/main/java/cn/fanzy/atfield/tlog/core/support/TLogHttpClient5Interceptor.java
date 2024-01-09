package cn.fanzy.atfield.tlog.core.support;


import cn.fanzy.atfield.tlog.common.constant.TLogConstants;
import cn.fanzy.atfield.tlog.common.context.SpanIdGenerator;
import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.fanzy.atfield.tlog.common.spring.TLogSpringAware;
import cn.fanzy.atfield.tlog.common.utils.LocalhostUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * HttpClient5的拦截器
 * @author Bryan.Zhang
 * @since 1.3.3
 */
public class TLogHttpClient5Interceptor implements HttpRequestInterceptor {
    
    private static final Logger log = LoggerFactory.getLogger(TLogHttpClient5Interceptor.class);
    
    @Override
    public void process(HttpRequest request, EntityDetails entityDetails, HttpContext context) throws HttpException, IOException {
        String traceId = TLogContext.getTraceId();
        if(StrUtil.isNotBlank(traceId)) {
            String appName = TLogSpringAware.getProperty("spring.application.name");

            request.addHeader(TLogConstants.TLOG_TRACE_KEY, traceId);
            request.addHeader(TLogConstants.TLOG_SPANID_KEY, SpanIdGenerator.generateNextSpanId());
            request.addHeader(TLogConstants.PRE_IVK_APP_KEY, appName);
            request.addHeader(TLogConstants.PRE_IVK_APP_HOST, LocalhostUtil.getHostName());
            request.addHeader(TLogConstants.PRE_IP_KEY, LocalhostUtil.getHostIp());
        } else {
            log.debug("[TLOG]本地threadLocal变量没有正确传递traceId,本次调用不传递traceId");
        }
    }
}