package cn.fanzy.infra.tlog.core.aop;

import cn.fanzy.infra.tlog.common.constant.TLogConstants;
import cn.fanzy.infra.tlog.common.exception.TLogCustomLabelExpressionException;
import cn.fanzy.infra.tlog.core.annotation.TLogAspect;
import cn.fanzy.infra.tlog.core.context.AspectLogContext;
import cn.fanzy.infra.tlog.core.convert.AspectLogConvert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.InstructionSet;
import io.micrometer.common.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义埋点注解切面，用于拦截@AspectLogAop
 *
 * @author Bryan.Zhang
 * @since 1.1.0
 */
@Aspect
public class AspectLogAop {

    private static final Logger log = LoggerFactory.getLogger(AspectLogAop.class);

    private ExpressRunner expressRunner = new ExpressRunner();

    @Pointcut("@annotation(cn.fanzy.infra.tlog.core.annotation.TLogAspect)")
    public void cut() {
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        Map<String, Object> paramNameValueMap = MapUtil.newHashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            paramNameValueMap.put(parameterNames[i], args[i]);
        }

        TLogAspect tlogAspect = method.getAnnotation(TLogAspect.class);
        String[] aspectExpressions = tlogAspect.value();
        String str = tlogAspect.str();
        String pattern = tlogAspect.pattern();
        String joint = tlogAspect.joint();
        Class<? extends AspectLogConvert> convertClazz = tlogAspect.convert();

        StringBuilder sb = new StringBuilder();

        //处理字符串类型标签
        if (StrUtil.isNotBlank(str)){
            sb.append(str);
            sb.append(joint);
        }

        //处理自定义converter
        boolean isAspectLogConvert;
        if (convertClazz.equals(AspectLogConvert.class)){
            isAspectLogConvert = false;
        }else{
            isAspectLogConvert = AspectLogConvert.class.isAssignableFrom(convertClazz);
        }

        if (isAspectLogConvert) {
            // 原来使用的是newInstance，已过时，更改为.getDeclaredConstructor().newInstance()
            AspectLogConvert convert = convertClazz.getDeclaredConstructor().newInstance();
            try {
                sb.append(convert.convert(args));
                sb.append(joint);
            } catch (Throwable t) {
                log.error("[AspectLog]some errors happens in AspectLog's convert", t);
            }
        }

        //处理表达式
        for (String aspectExpression : aspectExpressions) {
            String aspLogValueItem = getExpressionValue(aspectExpression, paramNameValueMap);
            if (StringUtils.isNotBlank(aspLogValueItem)) {
                sb.append(StrUtil.format("{}:{}", aspectExpression, aspLogValueItem));
                sb.append(joint);
            }
        }

        String aspLogValue = sb.toString();
        if (StringUtils.isNotBlank(aspLogValue)) {
            aspLogValue = aspLogValue.substring(0, aspLogValue.length() - joint.length());
            aspLogValue = StrUtil.format(pattern, aspLogValue);

            //拿到之前的标签
            String currentLabel = AspectLogContext.getLogValue();

            MDC.put(TLogConstants.MDC_KEY, currentLabel + " " + aspLogValue);
            AspectLogContext.putLogValue(currentLabel + " " + aspLogValue);
        }

        return jp.proceed();
    }

    private String getExpressionValue(String expression, Map<String, Object> map){
        List<String> errorList = new ArrayList<>();
        try{
            InstructionSet instructionSet = expressRunner.getInstructionSetFromLocalCache("map." + expression);
            DefaultContext<String, Object> context = new DefaultContext<>();
            context.put("map", map);
            Object value = expressRunner.execute(instructionSet, context, errorList, false, false);

            if (ObjectUtil.isNull(value)){
                return null;
            }
            if (ObjectUtil.isBasicType(value)){
                return value.toString();
            }else{
                return JSONUtil.toJsonStr(value);
            }
        }catch (Throwable t){
            for (String scriptErrorMsg : errorList){
                log.error("\n{}", scriptErrorMsg);
            }
            log.error(t.getMessage(),t);
            throw new TLogCustomLabelExpressionException(t.getMessage());
        }
    }
}
