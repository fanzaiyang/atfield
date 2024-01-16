package cn.fanzy.atfield.satoken.login.annotation;


import java.lang.annotation.*;

/**
 * 登录
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    /**
     * 登录名
     * <pre>
     *     格式说明：#开头。
     *     1. #username -> 从请求参数里获取username
     *     2. #user.username -> 从参数user对象里获取username
     *      #persion.name
     *      #persons[3]
     *      #person.friends[5].name
     * </pre>
     * @return {@link String}
     */
    String value();
}
