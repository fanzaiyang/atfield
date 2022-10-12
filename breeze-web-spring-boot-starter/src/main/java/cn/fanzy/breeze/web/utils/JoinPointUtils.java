package cn.fanzy.breeze.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.checkerframework.checker.units.qual.A;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JoinPointUtils {
    /**
     * 得到参数
     *
     * @param joinPoint 连接点
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> getParams(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            Object arg = joinPoint.getArgs()[i];
            if (!(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse)) {
                param.put(parameterNames[i], arg);
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
     * @param joinPoint
     * @param paramName
     * @return
     * @author Lishuzhen
     */
    public static Object getParamByName(JoinPoint joinPoint, String paramName) {
        // 获取所有参数的值
        Map<String, Object> params = getParams(joinPoint);
        return params.get(paramName);
    }

}
