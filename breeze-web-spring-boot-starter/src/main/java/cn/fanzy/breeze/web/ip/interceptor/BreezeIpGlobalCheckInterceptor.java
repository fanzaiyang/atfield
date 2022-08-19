package cn.fanzy.breeze.web.ip.interceptor;

import cn.fanzy.breeze.web.ip.service.BreezeIpGlobalCheckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
public class BreezeIpGlobalCheckInterceptor implements HandlerInterceptor {

    private final BreezeIpGlobalCheckService breezeIpGlobalCheckService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        breezeIpGlobalCheckService.handler(new ServletWebRequest(request, response));
        return true;
    }
}
