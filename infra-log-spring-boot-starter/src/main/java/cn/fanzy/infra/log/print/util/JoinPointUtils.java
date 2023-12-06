package cn.fanzy.infra.log.print.util;

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
 * @author fanzaiyang
 */
@Slf4j
public class JoinPointUtils {
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

}
