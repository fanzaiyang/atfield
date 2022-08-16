package cn.fanzy.breeze.web.sensitive;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <p>
 * Json序列化脱敏注解
 * </p>
 * <p>
 * 使用方法如下： 在POJO的需要脱密的属性上加上
 *
 * <pre>
 *  &#64;SensitiveInfo(SensitiveEnum.MOBILE_PHONE)
 * </pre>
 * <p>
 * 即可
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = BreezeSensitiveSerialize.class)
public @interface BreezeSensitive {

    BreezeSensitiveEnum value();

}