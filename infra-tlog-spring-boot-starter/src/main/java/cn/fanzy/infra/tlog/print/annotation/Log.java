package cn.fanzy.infra.tlog.print.annotation;


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
    String appName() default "";

    /**
     * 业务名称
     *
     * @return String
     */
    String moduleName() default "";

    String methodName() default "";

    String operateType() default "";

    String remarks() default "";

    /**
     * 忽略
     *
     * @return boolean
     */
    boolean ignore() default false;
}
