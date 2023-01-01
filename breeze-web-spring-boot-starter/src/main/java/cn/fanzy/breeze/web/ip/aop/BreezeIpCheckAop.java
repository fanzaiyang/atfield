package cn.fanzy.breeze.web.ip.aop;

import cn.fanzy.breeze.web.exception.config.BreezeWebExceptionConfiguration;
import cn.fanzy.breeze.web.ip.annotation.BreezeIpCheck;
import cn.fanzy.breeze.web.ip.properties.BreezeIpProperties;
import cn.fanzy.breeze.web.ip.service.BreezeIpCheckService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @author fanzaiyang
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
@EnableConfigurationProperties(BreezeIpProperties.class)
@AutoConfigureBefore(BreezeWebExceptionConfiguration.class)
public class BreezeIpCheckAop {
    private final BreezeIpProperties properties;

    @Pointcut("@annotation(cn.fanzy.breeze.web.ip.annotation.BreezeIpCheck)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        String clientIp = SpringUtils.getClientIp();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        BreezeIpCheck ipCheck = method.getAnnotation(BreezeIpCheck.class);
        if (ipCheck == null) {
            return;
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

        String[] allowed = ipCheck.value();
        String[] deny = ipCheck.deny();
        if (allowed == null || allowed.length == 0) {
            allowed = properties.getAllowed();
        }
        if (deny == null || deny.length == 0) {
            deny = properties.getDeny();
        }

        // 黑名单验证
        if (deny != null && deny.length > 0) {
            if (ArrayUtil.contains(deny, clientIp)) {
                throw new RuntimeException("当前IP不允许访问此接口！");
            }
        }
        // 白名单验证
        if (allowed != null && allowed.length > 0) {
            if (!ArrayUtil.contains(allowed, clientIp)) {
                throw new RuntimeException("当前IP不允许访问此接口！");
            }
        }
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <IP-AOP校验> 相关的配置。");
    }
}
