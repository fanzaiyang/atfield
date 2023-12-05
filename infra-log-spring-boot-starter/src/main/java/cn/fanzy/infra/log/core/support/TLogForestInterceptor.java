package cn.fanzy.infra.log.core.support;


import cn.fanzy.infra.log.common.constant.TLogConstants;
import cn.fanzy.infra.log.common.context.SpanIdGenerator;
import cn.fanzy.infra.log.common.context.TLogContext;
import cn.fanzy.infra.log.common.spring.TLogSpringAware;
import cn.fanzy.infra.log.common.utils.LocalhostUtil;
import cn.hutool.core.util.StrUtil;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Forest的拦截器
 * @author Bryan.Zhang
 * @since 1.3.5
 */
public class TLogForestInterceptor implements Interceptor<Object> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean beforeExecute(ForestRequest request) {
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
        return true;
    }
}
