package cn.fanzy.breeze.web.ip.annotation;

import cn.fanzy.breeze.web.ip.service.BreezeIpCheckService;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BreezeIpCheck {
    /**
     * 允许的IP
     * @return String[]
     */
    String[] value() default {};

    /**
     * 黑名单
     * @return String[]
     */
    String[] deny() default {};

    /**
     * 自定义处理程序
     *
     * @return {@link Class}<{@link ?} {@link extends} {@link BreezeIpCheckService}>
     */
    Class<? extends BreezeIpCheckService> handler() default BreezeIpCheckService.class;
}
