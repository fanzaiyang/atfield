package cn.fanzy.breeze.web.response.annotation;

import java.lang.annotation.*;

/**
 * @author fanzaiyang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseWrapper {
}
