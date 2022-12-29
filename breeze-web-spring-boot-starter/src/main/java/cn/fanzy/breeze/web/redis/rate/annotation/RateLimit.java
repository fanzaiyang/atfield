package cn.fanzy.breeze.web.redis.rate.annotation;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 限流的key
     * @return
     */
    String key() default "";

    /**
     * 限流模式,默认单机
     * @return
     */
    RateType type() default RateType.PER_CLIENT;

    /**
     * 限流速率，默认每秒10
     * @return
     */
    long rate() default 1;

    /**
     * 限流速率
     * @return
     */
    long rateInterval() default 1000;

    /**
     * 限流速率单位
     * @return
     */
    RateIntervalUnit timeUnit() default RateIntervalUnit.SECONDS;

    boolean useIp() default false;
}
