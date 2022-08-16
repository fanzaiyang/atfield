package cn.fanzy.breeze.web.ip.aop;

import cn.fanzy.breeze.web.ip.annotation.BreezeIpCheck;
import cn.fanzy.breeze.web.ip.service.BreezeIpCheckService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class BreezeIpCheckAop {
    @Pointcut("@annotation(cn.fanzy.breeze.web.ip.annotation.BreezeIpCheck)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        String clientIp = SpringUtils.getClientIp();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        BreezeIpCheck ipCheck = method.getAnnotation(BreezeIpCheck.class);
        // 白名单验证
        if (ipCheck.value() != null && ipCheck.value().length > 0) {
            if (!ArrayUtil.contains(ipCheck.value(), clientIp)) {
                throw new RuntimeException("当前IP不允许访问此接口！");
            }
        }
        // 黑名单验证
        if (ipCheck.deny() != null && ipCheck.deny().length > 0) {
            if (ArrayUtil.contains(ipCheck.deny(), clientIp)) {
                throw new RuntimeException("当前IP不允许访问此接口！");
            }
        }
        // 处理自定义验证
        if (ipCheck.handler() != null) {
            try {
                BreezeIpCheckService handler = ipCheck.handler().getDeclaredConstructor().newInstance();
                handler.handler();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启IP校验增强组件。");
    }
}
