package cn.fanzy.breeze.web.code.annotation;


import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.code.enums.IBreezeCodeTypeEnum;
import cn.fanzy.breeze.web.ip.service.BreezeIpCheckService;

import java.lang.annotation.*;

/**
 * 微风代码检查器
 *
 * @author fanzaiyang
 * @since 2022-08-19
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BreezeCodeChecker {

    /**
     * 执行验证码类型CodeType,默认图形验证码
     *
     * @return {@link BreezeCodeType}
     */
    BreezeCodeType value() default BreezeCodeType.IMAGE;
}
