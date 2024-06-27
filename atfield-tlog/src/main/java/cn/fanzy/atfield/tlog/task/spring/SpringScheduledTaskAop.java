package cn.fanzy.atfield.tlog.task.spring;

import cn.fanzy.atfield.tlog.core.rpc.TLogLabelBean;
import cn.fanzy.atfield.tlog.core.rpc.TLogRPCHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 基于spring scheduled定时器的增强AOP
 *
 * @author Bryan.Zhang
 * @since 1.3.4
 */
@Slf4j
@Aspect
public class SpringScheduledTaskAop {

    private final TLogRPCHandler tLogRPCHandler = new TLogRPCHandler();

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void cut() {
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        try {
            tLogRPCHandler.processProviderSide(new TLogLabelBean());
            return jp.proceed();
        } finally {
            tLogRPCHandler.cleanThreadLocal();
        }
    }
}
