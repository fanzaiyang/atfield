package cn.fanzy.breeze.web.safe.aop;

import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import cn.fanzy.breeze.web.safe.properties.BreezeSafeProperties;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import cn.fanzy.breeze.web.utils.JoinPointUtils;
import cn.fanzy.breeze.web.utils.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

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

    private String loginId="";
    @Pointcut("@annotation(cn.fanzy.breeze.web.safe.annotation.BreezeSafe)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        Object param = JoinPointUtils.getParamByName(jp, properties.getLoginKey());
        if (param == null) {
            log.warn("请求参数：「{}」为空！", properties.getLoginKey());
            return;
        }
        loginId=param.toString();
        breezeSafeService.check(loginId, JoinPointUtils.getAnnotation(jp, BreezeSafe.class));
    }

    /**
     * 当方法抛出异常的时候，记录次数！
     *
     * @param e
     */
    @AfterThrowing(value = "cut()",throwing = "e")
    public void afterThrowing(Throwable e) {
        breezeSafeService.count(loginId);
    }

}
