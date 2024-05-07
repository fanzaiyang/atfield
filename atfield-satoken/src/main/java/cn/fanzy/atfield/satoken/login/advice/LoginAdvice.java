package cn.fanzy.atfield.satoken.login.advice;


import cn.fanzy.atfield.cache.CacheService;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;
import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.core.utils.AopUtil;
import cn.fanzy.atfield.satoken.login.annotation.Login;
import cn.fanzy.atfield.satoken.login.exception.LoginDenyException;
import cn.fanzy.atfield.satoken.login.model.LoginAopInfoDto;
import cn.fanzy.atfield.satoken.login.property.LoginProperty;
import cn.fanzy.atfield.satoken.login.service.LoginAdviceService;
import cn.fanzy.atfield.satoken.login.utils.LoginStorageUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * captcha检查建议
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@ConditionalOnClass(ICaptchaType.class)
@EnableConfigurationProperties({LoginProperty.class})
public class LoginAdvice {

    private final LoginAdviceService loginAdviceService;
    private final LoginProperty property;
    private final TransmittableThreadLocal<LoginAopInfoDto> threadLocal = new TransmittableThreadLocal<>();

    @Pointcut("@annotation(cn.fanzy.atfield.satoken.login.annotation.Login)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        Login annotation = AopUtil.getAnnotation(jp, Login.class);
        if (annotation == null) {
            return;
        }
        if (property.getRetryCount() == null) {
            return;
        }
        if (property.getRetryCount() < 0) {
            // 不限制次数的登录
            return;
        }
        if (property.getRetryCount() == 0) {
            // 不允许登录
            throw new LoginDenyException("当前系统不允许登录，请稍后再试~");
        }
        // 获取登录名
        String value = annotation.value();
        Assert.isTrue(StrUtil.startWith(value, "#"), "注解value值设置错误，必须以“#”开头。");
        String loginKey = value.substring(1);
        // 解析登录名
        Map<String, Object> requestParams = SpringUtils.getRequestParams();
        String loginName;
        if (!StrUtil.contains(loginKey, StrPool.DOT)) {
            loginName = MapUtil.getStr(requestParams, loginKey);
        } else {
            Object byPath = JSONUtil.getByPath(JSONUtil.parseObj(requestParams), loginKey);
            loginName = byPath == null ? "" : byPath.toString();
        }
        // 获取登录次数
        String clientIp = SpringUtils.getClientIp();
        Integer count = SpringUtils.getBean(CacheService.class)
                .get(LoginStorageUtil.getKey(loginName, clientIp, property), Integer.class);
        if (count == null) {
            count = 0;
        }
        LoginAopInfoDto dto = LoginAopInfoDto.builder()
                .loginKey(loginKey).loginName(loginName)
                .clientIp(clientIp)
                .storageKey(LoginStorageUtil.getKey(loginName, clientIp, property))
                .errorCount(count)
                .retryCount(property.getRetryCount())
                .showCaptchaCount(property.getShowCaptchaCount())
                .lockSeconds(property.getLockSeconds())
                .build();
        threadLocal.set(dto);
        loginAdviceService.check(dto);
    }

    /**
     * 登录成功，需要清空失败记录
     *
     * @param obj OBJ
     */
    @AfterReturning(returning = "obj", value = "cut()")
    public void afterReturning(Object obj) {
        loginAdviceService.success(threadLocal.get());
    }

    /**
     * 登录异常，需要记录失败次数
     *
     * @param e e
     */
    @AfterThrowing(throwing = "e", value = "cut()")
    public void afterThrowing(Throwable e) {
        LoginAopInfoDto dto = threadLocal.get();
        // 失败次数+1
        dto.setErrorCount(dto.getErrorCount() + 1);
        dto.setLockTime(new Date());
        loginAdviceService.error(dto);
    }
}
