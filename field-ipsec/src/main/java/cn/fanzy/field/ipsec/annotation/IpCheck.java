package cn.fanzy.field.ipsec.annotation;

import java.lang.annotation.*;

/**
 * IP校验,用于某个接口的访问IP校验
 *
 * @author fanzaiyang
 * @date 2023/12/07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IpCheck {

    /**
     * 允许访问的IP集合
     *
     * @return {@link String[]}
     */
    String[] value() default {};

    /**
     * 拒绝访问的IP集合
     *
     * @return {@link String[]}
     */
    String[] deny() default {};
}
