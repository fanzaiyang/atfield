package cn.fanzy.field.redis.annotation;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.lang.annotation.*;

/**
 * 限流
 * 默认key：LOCK_RATE_LIMIT，全局生效（不基于IP限流）
 * @author fanzaiyang
 * @date 2023/12/06
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockRate {
    /**
     * 限流的key
     * @return String
     */
    String value() default "LOCK_RATE_LIMIT";

    /**
     * 限流模式,默认单机
     * @return RateType
     */
    RateType type() default RateType.PER_CLIENT;

    /**
     * 限流速率，默认:1秒
     * @return long
     */
    long rate() default 1;

    /**
     * 限流速率,默认10次
     * @return long
     */
    long rateInterval() default 10;

    /**
     * 限流速率单位
     * @return RateIntervalUnit
     */
    RateIntervalUnit timeUnit() default RateIntervalUnit.SECONDS;

    /**
     * 使用ip
     *
     * @return boolean
     */
    boolean useIp() default false;
}
