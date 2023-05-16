package cn.fanzy.breeze.web.logs.annotation;

import cn.fanzy.breeze.web.logs.enums.LogTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fanzaiyang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    /**
     * 模块名称
     *
     * @return {@link String}
     */
    String module() default "";
    /**
     * 业务名称
     *
     * @return String
     */
    String value() default "";

    /**
     * 日志类型,默认：未知
     *
     * @return {@link LogTypeEnum}
     */
    LogTypeEnum type() default LogTypeEnum.NONE;

    /**
     * 请求参数中的ID
     *
     * @return {@link String}
     */
    String userIdKey() default "";

    String userName() default "";

    /**
     * 忽略
     *
     * @return boolean
     */
    boolean ignore() default false;

    /**
     * 应用id
     *
     * @return {@link String}
     */
    String appIdKey() default "";

    String appName() default "";
}
