package cn.fanzy.infra.log.support;

import cn.fanzy.infra.log.common.constant.TLogConstants;
import cn.fanzy.infra.log.common.context.SpanIdGenerator;
import cn.fanzy.infra.log.common.context.TLogContext;
import cn.fanzy.infra.log.common.spring.TLogSpringAware;
import cn.fanzy.infra.log.common.utils.LocalhostUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * RestTemplate的拦截器
 * @author Bryan.Zhang
 * @since 1.3.6
 */
public class TLogRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String traceId = TLogContext.getTraceId();
        if(StrUtil.isNotBlank(traceId)) {
            String appName = TLogSpringAware.getProperty("spring.application.name");

            request.getHeaders().add(TLogConstants.TLOG_TRACE_KEY, traceId);
            request.getHeaders().add(TLogConstants.TLOG_SPANID_KEY, SpanIdGenerator.generateNextSpanId());
            request.getHeaders().add(TLogConstants.PRE_IVK_APP_KEY, appName);
            request.getHeaders().add(TLogConstants.PRE_IVK_APP_HOST, LocalhostUtil.getHostName());
            request.getHeaders().add(TLogConstants.PRE_IP_KEY, LocalhostUtil.getHostIp());
        } else {
            log.debug("[TLOG]本地threadLocal变量没有正确传递traceId,本次调用不传递traceId");
        }
        return execution.execute(request, body);
    }
}
