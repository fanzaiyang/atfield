package cn.fanzy.infra.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {
    /**
     * 锁名称
     *
     * @return String
     */
    String value();

    /**
     * 过期时间
     *
     * @return long
     */
    long leaseTime() default 0;

    /**
     * 时间单位，默认：秒
     *
     * @return TimeUnit
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 尝试等待时间
     *
     * @return long
     */
    long tryWaitTime() default 0;

    /**
     * 尝试错误消息
     *
     * @return {@link String}
     */
    String errorMessage() default "该方法被另外一个线程占用，请稍后再试！";
}
