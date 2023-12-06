package cn.fanzy.infra.redis.annotation;

import cn.fanzy.infra.redis.enums.SubmitType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockForm {
    /**
     * 锁名称
     *
     * @return String
     */
    String value();

    /**
     * 加锁方式
     *
     * @return {@link SubmitType}
     */
    SubmitType type() default SubmitType.IP_AND_PARAM;

    /**
     * 尝试加锁等待时间
     *
     * @return long
     */
    long waitTime() default 0;

    /**
     * 锁时间
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

    String errorMessage() default "请不要重复提交！";
}
