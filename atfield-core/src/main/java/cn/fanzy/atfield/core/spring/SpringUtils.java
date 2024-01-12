package cn.fanzy.atfield.core.spring;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * spring上下文
 *
 * @author fanzaiyang
 * @date 2024-01-10
 * @since 17
 */
@Slf4j
public class SpringUtils extends SpringUtil {
    /**
     * 获得响应
     *
     * @return {@link HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获得请求
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取客户端ip
     *
     * @return {@link String}
     */
    public static String getClientIp() {
        return getClientIp(getRequest());
    }

    /**
     * 获取客户端ip
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * get请求方法:GET, POST, or PUT
     *
     * @return {@link String}
     */
    public static String getRequestMethod() {
        try {
            return getRequest().getMethod();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 得到请求uri
     *
     * @return {@link String}
     */
    public static String getRequestUri() {
        try {
            return getRequest().getRequestURI();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到查询字符串
     *
     * @return {@link String}
     */
    public static String getQueryString() {
        try {
            return getRequest().getQueryString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRequestUrl() {
        try {
            return getRequest().getRequestURL().toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isJson() {
        try {
            return isJson(getRequest());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否是json请求
     *
     * @param request 请求
     * @return boolean
     */
    public static boolean isJson(HttpServletRequest request) {
        if (request.getContentType() == null) {
            return false;
        }
        return StrUtil.equalsIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE) ||
                StrUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 获取系统进程PID
     *
     * @return int
     */
    public static int getCurrentProcessId() {
        try {
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            return Integer.parseInt(runtimeMXBean.getName().split("@")[0]);
        } catch (Exception e) {
            return 000000;
        }
    }

    public static Map<String, Object> getRequestParams() {
        return getRequestParams(getRequest());
    }

    /**
     * 获取请求参数
     *
     * @param request 要求
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>(1);
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            String[] paramArr = parameterMap.get(key);
            if (paramArr != null && paramArr.length == 1) {
                params.put(key, paramArr[0]);
                continue;
            }
            params.put(key, paramArr);
        }
        if (SpringUtils.isJson(request)) {
            String jsonParam = new RequestWrapper(request).getBodyString();
            if (JSONUtil.isTypeJSON(jsonParam)) {
                if (JSONUtil.isTypeJSONArray(jsonParam)) {
                    params.put("body", JSONUtil.parseArray(jsonParam));
                } else if (JSONUtil.isTypeJSONObject(jsonParam)) {
                    JSONObject obj = JSONUtil.parseObj(jsonParam);
                    for (Map.Entry<String, Object> entry : obj) {
                        params.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    params.put("unknown", jsonParam);
                }
            }
        }
        return params;

    }
}
