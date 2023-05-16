package cn.fanzy.breeze.sqltoy.annotation;

import java.lang.annotation.*;

/**
 * 逻辑删除
 * <h1>暂时还未实现注解方式</h1>
 * @author fanzaiyang
 * @since  2023-03-16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogicDelete {

    /**
     * 删除值，不填写默认读取配置
     * @return String
     */
    String deleteValue() default "";
    /**
     * 未删除值，不填写默认读取配置
     * @return String
     */
    String notDeleteValue() default "";
}
