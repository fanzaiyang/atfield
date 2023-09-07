package cn.fanzy.breeze.web.redis.lock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 锁定分布式
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockDistributed {
    /**
     * 锁名称
     *
     * @return String
     */
    String value() default "breeze_lock";

    /**
     * 过期时间
     *
     * @return long
     */
    long time() default 0;

    /**
     * 时间单位，默认：秒
     *
     * @return TimeUnit
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 是否使用tryLock，默认：false
     * @return boolen
     */
    boolean isTryLock() default false;

    /**
     * 尝试等待时间,isTry()为true时生效
     *
     * @return long
     */
    long tryWaitTime() default 0;

    /**
     * 是否抛出异常,isTry()为true时生效
     *
     * @return boolean
     */
    boolean tryThrowException() default false;

    /**
     * 尝试错误消息
     *
     * @return {@link String}
     */
    String tryErrorMessage() default "该方法被另外一个线程占用，请稍后再试！";
}
