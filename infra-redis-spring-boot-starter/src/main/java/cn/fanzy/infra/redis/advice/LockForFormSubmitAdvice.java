package cn.fanzy.infra.redis.advice;

import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.core.utils.ParamUtil;
import cn.fanzy.infra.redis.annotation.LockForm;
import cn.fanzy.infra.redis.enums.FormSubmitType;
import cn.fanzy.infra.redis.exception.LockFormException;
import cn.fanzy.infra.redis.util.AdviceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * 防止重复提交aop
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
@ConditionalOnClass(RedissonClient.class)
public class LockForFormSubmitAdvice {
    private final RedissonClient redissonClient;

    @Pointcut("@annotation(cn.fanzy.infra.redis.annotation.LockForm)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) throws InterruptedException {
        LockForm annotation = AdviceUtil.getAnnotation(jp, LockForm.class);
        if (annotation == null) {
            return;
        }
        String lockName = getLockName(annotation);
        RLock rlock = redissonClient.getLock(lockName);
        boolean tryLock = rlock.tryLock(annotation.waitTime(), annotation.leaseTime(), annotation.unit());
        if (!tryLock) {
            log.warn("请不要重复提交！执行方法：{}，{}", AdviceUtil.getMethodInfo(jp), rlock.getName());
            throw new LockFormException("400", "请不要重复提交!");
        }
    }

    @After("cut()")
    public void after(JoinPoint jp) {
        LockForm annotation = AdviceUtil.getAnnotation(jp,LockForm.class);
        if (annotation == null) {
            return;
        }
        String lockName = getLockName(annotation);
        RLock lock = redissonClient.getLock(lockName);
        if (lock.isLocked() || lock.isHeldByCurrentThread()) {
            try {
                lock.unlock();
            } catch (Exception e) {
                lock.forceUnlock();
            }

        }
    }

    private String getLockName(LockForm annotation){
        String lockName = AdviceUtil.getLockKey(annotation.value());

        // 基于IP地址的重复提交，即1个IP地址1个锁，。防止同一个IP重复提交
        if (FormSubmitType.IP.equals(annotation.type())) {
            // 更改锁名称，添加ip地址
            lockName = lockName + StrPool.COLON + SpringUtils.getClientIp();
        } else if (FormSubmitType.PARAM.equals(annotation.type())) {
            // 基于请求参数的重复提交，即1个请求参数1个锁，防止同一个请求参数重复提交
            String paramAscii = ParamUtil.getParamAscii(SpringUtils.getRequestParams());
            if (StrUtil.isBlank(paramAscii)) {
                lockName = lockName + StrPool.COLON + "NULL";
            } else {
                lockName = lockName + StrPool.COLON + paramAscii;
            }
        } else if (FormSubmitType.IP_AND_PARAM.equals(annotation.type())) {
            // 基于IP地址和请求参数的重复提交，即1个IP地址+1个请求参数1个锁，防止同一个IP地址和请求参数重复提交
            lockName = lockName + StrPool.COLON + SpringUtils.getClientIp();
            String paramAscii = ParamUtil.getParamAscii(SpringUtils.getRequestParams());
            if (StrUtil.isBlank(paramAscii)) {
                lockName = lockName + StrPool.COLON + "NULL";
            } else {
                lockName = lockName + StrPool.COLON + paramAscii;
            }
        }
        return lockName;
    }
}