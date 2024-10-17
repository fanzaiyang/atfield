package cn.fanzy.atfield.tlog.print.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author fanzaiyang
 * @date 2024/09/09
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecord {

    /**
     * 业务单号
     *
     * @return {@link String }
     */
    String bizNo() default "{{#bizNo}}";

    /**
     * 模块名称
     *
     * @return {@link String}
     */
    String appName() default "";

    /**
     * 操作人
     *
     * @return {@link String }
     */
    String operator() default "";

    /**
     * 操作类型
     *
     * @return {@link String }
     */
    String operateType();


    /**
     * 成功
     *
     * @return boolean
     */
    String content();

    /**
     * 额外
     *
     * @return {@link String }
     */
    String extra() default "";

}
