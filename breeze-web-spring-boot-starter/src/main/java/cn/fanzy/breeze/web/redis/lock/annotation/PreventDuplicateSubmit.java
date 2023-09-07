package cn.fanzy.breeze.web.redis.lock.annotation;

import cn.fanzy.breeze.web.redis.lock.enums.PreventSubmitTypeEnum;

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
public @interface PreventDuplicateSubmit {
    /**
     * 锁名称
     *
     * @return String
     */
    String value() default "lock_breeze_prevent_duplicate_submit";

    /**
     * 加锁方式
     *
     * @return {@link PreventSubmitTypeEnum}
     */
    PreventSubmitTypeEnum type() default PreventSubmitTypeEnum.IP_AND_PARAM;

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

    String errorMessage() default "该方法被另外一个线程占用，请稍后再试！";
}
