package cn.fanzy.infra.core.utils;

import cn.fanzy.infra.core.exception.GlobalException;
import cn.fanzy.infra.core.spring.SpringUtils;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * AOP工具类
 * <pre>
 *     需要添加aspectj依赖
 * &lt;dependency&gt;
 *     &lt;groupId&gt;org.aspectj&lt;/groupId&gt;
 *     &lt;artifactId&gt;aspectjweaver&lt;/artifactId&gt;
 * &lt;/dependency&gt;
 * </pre>
 * @author fanzaiyang
 * @date 2023/12/07
 */
@Slf4j
public class AdviceUtil {
    /**
     * 得到参数
     *
     * @param joinPoint 连接点
     * @return {@link Map} {@link String}, {@link Object}
     */
    public static Map<String, Object> getParams(JoinPoint joinPoint) {

        Map<String, Object> param = new HashMap<>(2);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            Object arg = joinPoint.getArgs()[i];
            if (!(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse)) {
                try {
                    JSONObject entries = JSONUtil.parseObj(arg);
                    for (Map.Entry<String, Object> entry : entries) {
                        param.put(entry.getKey(), entry.getValue());
                    }
                } catch (Exception e) {
                    param.put(parameterNames[i], arg);
                }
            }
        }
        return param;
    }

    public static <A extends Annotation> A getAnnotation(JoinPoint joinPoint, Class<A> clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(clazz);
    }

    /**
     * 获取执行的方法名。class.method
     *
     * @param joinPoint 连接点
     * @return {@link String}
     */
    public static String getMethodInfo(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    }

    /**
     * 从 joinPoint 中 根据 参数名称 获取参数
     *
     * @param joinPoint JoinPoint
     * @param paramName String
     * @return Object
     */
    public static Object getParamByName(JoinPoint joinPoint, String paramName) {
        // 获取所有参数的值
        Map<String, Object> params = getParams(joinPoint);
        return params.get(paramName);
    }

    public static String getLockKey(String value) {
        String lockName = value;
        if (StrUtil.startWith(value, StrPool.AT)) {
            value = value.replace(value, StrPool.AT);
            // @开头说明是请求参数
            Map<String, Object> requestParams = SpringUtils.getRequestParams();
            lockName = requestParams.get(value) == null ? null : requestParams.get(value).toString();
            if (StrUtil.isBlank(lockName)) {
                // 取Header
                lockName = SpringUtils.getRequest().getHeader(value);
            }
        }
        if (StrUtil.isBlank(value)) {
            throw new GlobalException("4001", "未找到对应key!");
        }
        return lockName;
    }

}
