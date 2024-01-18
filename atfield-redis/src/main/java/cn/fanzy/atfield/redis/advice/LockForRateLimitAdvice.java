package cn.fanzy.atfield.redis.advice;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.core.utils.AopUtil;
import cn.fanzy.atfield.redis.annotation.LockRate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author fanzaiyang
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
@ConditionalOnClass(RedissonClient.class)
public class LockForRateLimitAdvice {
    private final RedissonClient redissonClient;

    @Pointcut("@annotation(cn.fanzy.atfield.redis.annotation.LockRate)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) {
        LockRate annotation = AopUtil.getAnnotation(jp, LockRate.class);
        if (annotation == null) {
            return;
        }
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(getRateKey(jp));
        if (!rateLimiter.isExists()) {
            rateLimiter.delete();
            rateLimiter.trySetRate(annotation.type(), annotation.rate(), annotation.rateInterval(), annotation.timeUnit());
        }
        rateLimiter.clearExpire();
        boolean acquire = rateLimiter.tryAcquire();
        if (!acquire) {
            throw new RuntimeException(annotation.useIp() ? "请求过于频繁，请稍后在试～" : "当前访问用户太多，请稍后在试～");
        }
    }

    @After("cut()")
    public void after(JoinPoint jp) {
        // 此处不需要释放锁
    }


    private String getRateKey(JoinPoint jp) {
        LockRate annotation = AopUtil.getAnnotation(jp, LockRate.class);
        String key = annotation.value();
        if (annotation.useIp()) {
            // 基于IP地址限流
            key += ":" + SpringUtils.getClientIp();
        }
        // 拼接执行的方法
        String methodInfo = AopUtil.getMethodInfo(jp);
        key += ":" + methodInfo;
        return key;
    }
}
