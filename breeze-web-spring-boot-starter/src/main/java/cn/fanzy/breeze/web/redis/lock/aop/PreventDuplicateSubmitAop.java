package cn.fanzy.breeze.web.redis.lock.aop;

import cn.fanzy.breeze.core.utils.ParamUtil;
import cn.fanzy.breeze.web.redis.lock.annotation.PreventDuplicateSubmit;
import cn.fanzy.breeze.web.redis.lock.enums.PreventSubmitTypeEnum;
import cn.fanzy.breeze.web.redis.lock.exception.PreventDuplicateSubmitException;
import cn.fanzy.breeze.web.utils.JoinPointUtils;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

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
public class PreventDuplicateSubmitAop {
    private final RedissonClient redissonClient;

    @Pointcut("@annotation(cn.fanzy.breeze.web.redis.lock.annotation.PreventDuplicateSubmit)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint jp) throws InterruptedException {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        PreventDuplicateSubmit annotation = method.getAnnotation(PreventDuplicateSubmit.class);
        if (annotation == null) {
            return;
        }
        String lockName = "";
        String lockKey = annotation.value();
        if (StrUtil.startWith(lockKey, StrPool.AT)) {
            lockKey = lockKey.replace(lockKey, StrPool.AT);
            // @开头说明是请求参数
            Map<String, Object> requestParams = SpringUtils.getRequestParams();
            lockName = requestParams.get(lockKey) == null ? null : requestParams.get(lockKey).toString();
            if (StrUtil.isBlank(lockName)) {
                // 取Header
                lockName = SpringUtils.getRequest().getHeader(lockKey);
            }
        }
        // 如果未找到对应的请求参数，就使用全局锁。
        if (StrUtil.isBlank(lockName)) {
            log.warn("未找到「{}」的参数，使用全局锁{}", annotation.value(), lockKey);
            lockName = lockKey;
        }
        if (PreventSubmitTypeEnum.IP.equals(annotation.type())) {
            lockName = lockName + StrPool.COLON + SpringUtils.getClientIp();
        } else if (PreventSubmitTypeEnum.PARAM.equals(annotation.type())) {
            String paramAscii = ParamUtil.getParamAscii(SpringUtils.getRequestParams());
            if (StrUtil.isBlank(paramAscii)) {
                lockName = lockName + StrPool.COLON + "NULL";
            } else {
                lockName = lockName + StrPool.COLON + paramAscii;
            }
        } else if (PreventSubmitTypeEnum.IP_AND_PARAM.equals(annotation.type())) {
            lockName = lockName + StrPool.COLON + SpringUtils.getClientIp();
            String paramAscii = ParamUtil.getParamAscii(SpringUtils.getRequestParams());
            if (StrUtil.isBlank(paramAscii)) {
                lockName = lockName + StrPool.COLON + "NULL";
            } else {
                lockName = lockName + StrPool.COLON + paramAscii;
            }
        }
        RLock lock = redissonClient.getLock(lockName);
        boolean tryLock = lock.tryLock(annotation.waitTime(), annotation.leaseTime(), annotation.unit());
        if (!tryLock) {
            log.warn("该方法【{}】被另外一个线程占用，请稍后再试！", JoinPointUtils.getMethodInfo(jp));
            throw new PreventDuplicateSubmitException(annotation.errorMessage());
        }
    }

    @After("cut()")
    public void after(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        PreventDuplicateSubmit lockDistributed = method.getAnnotation(PreventDuplicateSubmit.class);
        if (lockDistributed == null) {
            return;
        }
        RLock lock = redissonClient.getLock(lockDistributed.value());
        if (lock.isLocked() || lock.isHeldByCurrentThread()) {
            try {
                lock.unlock();
            } catch (Exception e) {
                lock.forceUnlock();
            }

        }
    }
}
