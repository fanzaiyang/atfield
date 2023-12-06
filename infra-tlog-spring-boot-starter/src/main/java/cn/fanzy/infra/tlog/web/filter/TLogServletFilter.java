package cn.fanzy.infra.tlog.web.filter;

import cn.fanzy.infra.tlog.common.constant.TLogConstants;
import cn.fanzy.infra.tlog.common.context.TLogContext;
import cn.fanzy.infra.tlog.web.common.TLogWebCommon;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 支持servlet
 * @author Bryan.Zhang
 * @since 1.3.5
 */
public class TLogServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse){
            try{
                TLogWebCommon.loadInstance().preHandle((HttpServletRequest)request);
                //把traceId放入response的header，为了方便有些人有这样的需求，从前端拿整条链路的traceId
                ((HttpServletResponse)response).addHeader(TLogConstants.TLOG_TRACE_KEY, TLogContext.getTraceId());
                chain.doFilter(request, response);
                return;
            }finally {
                TLogWebCommon.loadInstance().afterCompletion();
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
