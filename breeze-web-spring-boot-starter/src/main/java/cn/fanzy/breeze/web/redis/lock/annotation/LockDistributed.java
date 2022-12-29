package cn.fanzy.breeze.web.redis.lock.annotation;

import java.util.concurrent.TimeUnit;

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
     * @return
     */
    long time() default 0;

    /**
     * 时间单位，默认：秒
     *
     * @return TimeUnit
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 使用tryLock
     */
    boolean isTry() default false;
}
