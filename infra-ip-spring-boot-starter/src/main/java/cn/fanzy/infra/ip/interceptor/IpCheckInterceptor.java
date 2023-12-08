package cn.fanzy.infra.ip.interceptor;

import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.ip.bean.IpStorageBean;
import cn.fanzy.infra.ip.service.IpCheckService;
import cn.fanzy.infra.ip.service.IpStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class IpCheckInterceptor implements HandlerInterceptor {
    private final IpCheckService ipCheckService;
    private final IpStorageService ipStorageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IpStorageBean storage = ipStorageService.getIpStorage();
        ipCheckService.check(SpringUtils.getClientIp(request), storage);
        return true;
    }
}
