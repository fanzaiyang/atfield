package cn.fanzy.atfield.tlog.web.interceptor;

import cn.fanzy.atfield.tlog.common.constant.TLogConstants;
import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.fanzy.atfield.tlog.web.common.TLogWebCommon;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;


/**
 * web controller的拦截器
 *
 * @author Bryan.Zhang
 * @since 1.1.5
 */
@Slf4j
public class TLogWebInterceptor extends AbsTLogWebHandlerMethodInterceptor {

    @Override
    public boolean preHandleByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TLogWebCommon.loadInstance().preHandle(request);
        //把traceId放入response的header，为了方便有些人有这样的需求，从前端拿整条链路的traceId
        response.addHeader(TLogConstants.TLOG_TRACE_KEY, TLogContext.getTraceId());
        return true;
    }

    @Override
    public void postHandleByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletionByHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TLogWebCommon.loadInstance().afterCompletion();
    }
}
