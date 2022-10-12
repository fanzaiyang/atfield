package cn.fanzy.breeze.web.safe.annotation;

import cn.fanzy.breeze.web.code.enums.BreezeCodeType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BreezeSafe {
    /**
     * 执行验证码类型CodeType,默认图形验证码
     *
     * @return {@link BreezeCodeType}
     */
    BreezeCodeType value() default BreezeCodeType.IMAGE;
}
