package cn.fanzy.field.captcha.annotation;

import cn.fanzy.field.captcha.enums.CaptchaType;

import java.lang.annotation.*;

/**
 * captcha
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CaptchaCheck {
    /**
     * 验证码类型
     *
     * @return {@link CaptchaType}
     */
    CaptchaType value() default CaptchaType.IMAGE;

    /**
     * 从请求中获取短信验证码的发送目标(手机号)的参数，默认值：从配置文件取
     *
     * @return {@link String}
     */
    String codeKey() default "";

    /**
     * 请求中获取短信验证码对应的短信内容的参数，默认值：从配置文件取
     *
     * @return {@link String}
     */
    String codeValue() default "";
}
