package cn.fanzy.breeze.web.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

@Slf4j
public class JoinPointUtils {
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
        Object[] args = joinPoint.getArgs();
        log.info("所有参数：{}", JSONUtil.toJsonStr(args));
        // 获取方法签名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 在方法签名中获取所有参数的名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 根据参数名称拿到下标， 参数值的数组和参数名称的数组下标是一一对应的
        int index = ArrayUtils.indexOf(parameterNames, paramName);
        if(index<0){
            return null;
        }
        // 在参数数组中取出下标对应参数值
        Object obj = args[index];
        if (obj == null) {
           return null;
        }
        return obj;
    }

}
