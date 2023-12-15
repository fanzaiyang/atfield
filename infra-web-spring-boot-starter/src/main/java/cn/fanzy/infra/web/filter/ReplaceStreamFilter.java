package cn.fanzy.infra.web.filter;

import cn.fanzy.infra.core.spring.RequestWrapper;
import cn.fanzy.infra.core.spring.ServletUtil;
import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.web.json.model.Json;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * 更换流过滤器
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(DispatcherServlet.class)
public class ReplaceStreamFilter implements Filter {
    private String[] excludedPathArray;

    private static final String DEFAULT_FILTER_EXCLUDED_PATHS = "/static/*,*.html,*.js,*.ico,*.jpg,*.png,*.css";


    @Override
    public void init(FilterConfig filterConfig) {
        excludedPathArray = DEFAULT_FILTER_EXCLUDED_PATHS.split(",");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (!isFilterExcludeRequest(httpServletRequest)) {
                ServletRequest requestWrapper = new RequestWrapper(httpServletRequest);
                chain.doFilter(requestWrapper, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.error("[过滤器]发生系统异常！", e);
            String message = e.getMessage();
            if (e.getCause() != null) {
                message = e.getCause().getMessage() == null ? message : e.getCause().getMessage();
                if (e.getCause().getCause() != null) {
                    message = e.getCause().getCause().getMessage() == null ? message : e.getCause().getCause().getMessage();
                }
            }

            ServletUtil.out((HttpServletResponse) response, Json.fail(message).setData(e.getCause()));
        }
    }

    /**
     * 判断是否是过滤器直接放行的请求(主要用于静态资源的放行)
     *
     * @param request http请求
     * @return boolean
     * @return Boolen
     */
    private boolean isFilterExcludeRequest(HttpServletRequest request) {
        if (null != excludedPathArray && excludedPathArray.length > 0) {
            String url = request.getRequestURI();
            for (String ecludedUrl : excludedPathArray) {
                if (ecludedUrl.startsWith("*.")) {
                    // 如果配置的是后缀匹配, 则把前面的*号干掉，然后用endWith来判断
                    if (url.endsWith(ecludedUrl.substring(1))) {
                        return true;
                    }
                } else if (ecludedUrl.endsWith("/*")) {
                    if (!ecludedUrl.startsWith("/")) {
                        // 前缀匹配，必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;

                    }
                    // 如果配置是前缀匹配, 则把最后的*号干掉，然后startWith来判断
                    String prefixStr = request.getContextPath() + ecludedUrl.substring(0, ecludedUrl.length() - 1);
                    if (url.startsWith(prefixStr)) {
                        return true;
                    }
                } else {
                    // 如果不是前缀匹配也不是后缀匹配,那就是全路径匹配
                    if (!ecludedUrl.startsWith("/")) {
                        // 全路径匹配，也必须要是/开头
                        ecludedUrl = "/" + ecludedUrl;
                    }
                    String targetUrl = request.getContextPath() + ecludedUrl;
                    if (url.equals(targetUrl)) {
                        return true;
                    }
                }
            }
        }
        return !SpringUtils.isJson(request);
    }

    @PostConstruct
    public void init() {
        log.info("开启 <ReplaceStreamFilter> 相关的配置");
    }
}
