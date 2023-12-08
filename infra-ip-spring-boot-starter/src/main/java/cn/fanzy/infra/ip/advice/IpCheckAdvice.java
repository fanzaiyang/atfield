package cn.fanzy.infra.ip.advice;

import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.core.utils.AdviceUtil;
import cn.fanzy.infra.ip.annotation.IpCheck;
import cn.fanzy.infra.ip.bean.IpStorageBean;
import cn.fanzy.infra.ip.configuration.IpCheckConfiguration;
import cn.fanzy.infra.ip.property.IpProperty;
import cn.fanzy.infra.ip.service.IpCheckService;
import cn.fanzy.infra.ip.service.IpStorageService;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Aspect
@RestControllerAdvice
@AutoConfigureAfter(IpCheckConfiguration.class)
@EnableConfigurationProperties({IpProperty.class})
public class IpCheckAdvice {
    private final IpCheckService checkService;
    private final IpStorageService storageService;

    @Pointcut("@annotation(cn.fanzy.infra.ip.annotation.IpCheck)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        IpCheck annotation = AdviceUtil.getAnnotation(jp, IpCheck.class);
        if (annotation == null) {
            return;
        }
        // 注解优先
        IpStorageBean bean=new IpStorageBean();
        if(annotation.value()!=null){
            bean.setAllowedIpList(Arrays.stream(annotation.value()).collect(Collectors.toSet()));
        }
        if(annotation.deny()!=null){
            bean.setDeniedIpList(Arrays.stream(annotation.deny()).collect(Collectors.toSet()));
        }
        // 启用全局，把全局添加到自定义存储里
        if (annotation.global()) {
            IpStorageBean ipStorage = storageService.getIpStorage();
            if(CollUtil.isNotEmpty(ipStorage.getAllowedIpList())){
                Set<String> allowedIpList = bean.getAllowedIpList();
                allowedIpList.addAll(ipStorage.getAllowedIpList());
                bean.setAllowedIpList(allowedIpList);
            }
            if(CollUtil.isNotEmpty(ipStorage.getDeniedIpList())){
                Set<String> deniedIpList = bean.getDeniedIpList();
                deniedIpList.addAll(ipStorage.getDeniedIpList());
                bean.setDeniedIpList(deniedIpList);
            }
        }
        checkService.check(SpringUtils.getClientIp(),bean);
    }
}
