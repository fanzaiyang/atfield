package cn.fanzy.infra.web.response.annotation;

import cn.fanzy.infra.web.json.model.Json;

import java.lang.annotation.*;

/**
 * 响应包装器
 * 如果响应结果不是要求格式，可以使用此方式包装。
 * <pre>
 *     比如：返回数据String，前端要求返回对象{@link Json}。
 *     则可以使用此注解。帮助你把返回数据String包装到对象里。
 * </pre>
 *
 * @author fanzaiyang
 * @date 2023/12/07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseWrapper {
    Class<?> value() default Json.class;
}
