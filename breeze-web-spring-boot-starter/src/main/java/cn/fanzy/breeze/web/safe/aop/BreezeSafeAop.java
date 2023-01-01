package cn.fanzy.breeze.web.safe.aop;

import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import cn.fanzy.breeze.web.safe.properties.BreezeSafeProperties;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import cn.fanzy.breeze.web.safe.utils.BreezeSafeUtil;
import cn.fanzy.breeze.web.utils.JoinPointUtils;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author fanzaiyang
 */
@Slf4j
@Aspect
@EnableConfigurationProperties(BreezeSafeProperties.class)
public class BreezeSafeAop {
    private final BreezeSafeService breezeSafeService;
    private final BreezeSafeProperties properties;

    public BreezeSafeAop(BreezeSafeService breezeSafeService, BreezeSafeProperties properties) {
        this.breezeSafeService = breezeSafeService;
        this.properties = properties;
    }


    @Pointcut("@annotation(cn.fanzy.breeze.web.safe.annotation.BreezeSafe)")
    public void cut() {
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint jp) {
        BreezeSafe annotation = JoinPointUtils.getAnnotation(jp, BreezeSafe.class);
        String loginKey = StrUtil.blankToDefault(annotation.loginKey(), properties.getLoginKey());
        Object param = JoinPointUtils.getParamByName(jp, loginKey);
        String loginId = "";
        if (param != null) {
            loginId = param.toString();
            breezeSafeService.check(loginId, annotation);
        }
        try {
            Object proceed = jp.proceed();
            breezeSafeService.remove(loginId);
            return proceed;
        } catch (Throwable e) {
            breezeSafeService.count(loginId);
            return JsonContent.error(e.getMessage() + BreezeSafeUtil.getErrorMsg(loginId));
        }
    }

}
