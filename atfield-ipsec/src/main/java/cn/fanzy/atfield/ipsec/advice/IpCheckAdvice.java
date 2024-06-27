package cn.fanzy.atfield.ipsec.advice;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.core.utils.AopUtil;
import cn.fanzy.atfield.ipsec.annotation.IpCheck;
import cn.fanzy.atfield.ipsec.bean.IpStorageBean;
import cn.fanzy.atfield.ipsec.configuration.IpCheckConfiguration;
import cn.fanzy.atfield.ipsec.property.IpProperty;
import cn.fanzy.atfield.ipsec.service.IpCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Aspect
@AutoConfigureAfter(IpCheckConfiguration.class)
@EnableConfigurationProperties({IpProperty.class})
public class IpCheckAdvice {
    private final IpCheckService checkService;

    @Pointcut("@annotation(cn.fanzy.atfield.ipsec.annotation.IpCheck)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        IpCheck annotation = AopUtil.getAnnotation(jp, IpCheck.class);
        if (annotation == null) {
            return;
        }
        // 注解优先
        IpStorageBean bean = new IpStorageBean();
        if (annotation.value() != null) {
            bean.setAllowedIpList(Arrays.stream(annotation.value()).collect(Collectors.toSet()));
        }
        if (annotation.deny() != null) {
            bean.setDeniedIpList(Arrays.stream(annotation.deny()).collect(Collectors.toSet()));
        }
        checkService.check(SpringUtils.getClientIp(), bean);
    }
}
