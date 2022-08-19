package cn.fanzy.breeze.web.filter;

import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.utils.HttpUtil;
import cn.fanzy.breeze.web.utils.SpringUtils;
import com.yomahub.tlog.web.wrapper.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            log.error("发生系统异常！", e);
            String message = e.getMessage();
            if (e.getCause() != null) {
                message = e.getCause().getMessage();
                if (e.getCause().getCause() != null) {
                    message = e.getCause().getCause().getMessage();
                }
            }
            HttpUtil.out((HttpServletResponse) response, JsonContent.error(message).setData(e.getCause()));
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 判断是否是过滤器直接放行的请求(主要用于静态资源的放行)
     *
     * @return boolean
     * @Param request http请求
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
                    String prffixStr = request.getContextPath() + ecludedUrl.substring(0, ecludedUrl.length() - 1);
                    if (url.startsWith(prffixStr)) {
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
        log.info("「微风组件」: 开启 <ReplaceStreamFilter> 相关的配置");
    }
}
