package cn.fanzy.breeze.web.model;

import cn.fanzy.breeze.web.model.utils.BreezeModelContext;
import cn.hutool.core.date.DateUtil;
import com.yomahub.tlog.context.TLogContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * @author 黑山老妖
 * @date 2018/9/19
 */

/**
 * 统一响应结果
 *
 * @author wanghuifeng
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class JsonContent<T> {
    private String id;
    private int code;

    private String message;

    private T data;

    private String now = DateUtil.now();

    private boolean success = false;

    private Object exData;

    public JsonContent() {
    }

    public JsonContent(int code, String message) {
        this.code = code;
        this.message = message;
        this.success = code == BreezeModelContext.properties.getSuccessCode();
        this.now = DateUtil.now();
    }


    public JsonContent(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = code == BreezeModelContext.properties.getSuccessCode();
        this.now = DateUtil.now();
        this.data = data;
    }

    /**
     * 成功
     *
     * @return JsonContent
     */
    public static <T> JsonContent<T> success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param object 对象
     * @return {@link JsonContent}<{@link T}>
     */
    public static <T> JsonContent<T> success(T object) {
        return new JsonContent<>(BreezeModelContext.properties.getSuccessCode(), BreezeModelContext.properties.getSuccessMessage(), object);
    }

    /**
     * 失败
     *
     * @return {@link JsonContent}<{@link T}>
     */
    public static <T> JsonContent<T> error() {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), BreezeModelContext.properties.getErrorMessage());
    }

    /**
     * 失败
     *
     * @return JsonContent
     */
    public static <T> JsonContent<T> error(int errorCode, String errorMessage) {
        return new JsonContent<>(errorCode, errorMessage);
    }

    /**
     * 失败
     *
     * @return JsonContent
     */
    public static <T> JsonContent<T> error(int errorCode, Exception e) {
        return new JsonContent<>(errorCode, e.getMessage());
    }

    /**
     * 失败
     *
     * @return JsonContent
     */
    public static <T> JsonContent<T> error(Exception exception) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), exception.getMessage());
    }

    /**
     * 错误
     * 失败
     *
     * @param errorMessage 错误消息
     * @return JsonContent
     */
    public static <T> JsonContent<T> error(String errorMessage) {
        return error(new RuntimeException(errorMessage));
    }

    /**
     * 生成一个默认的表示参数有误的响应对象(响应码400)
     *
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam() {
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), "请检查参数！");
    }

    /**
     * 根据响应提示信息生成一个表示参数有误的响应对象(响应码400)
     *
     * @param message 响应提示信息
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam(String message) {
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 根据响应提示信息和响应数据生成一个表示参数有误的响应对象(响应码400)
     *
     * @param <T>     响应数据的数据类型
     * @param message 响应提示信息
     * @param data    响应数据
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam(String message, T data) {
        return new JsonContent(HttpStatus.BAD_REQUEST.value(), message, data);
    }

    /**
     * 生成一个默认的表示资源未授权的响应对象(401响应码)
     *
     * @return 表示资源未授权的响应对象(401响应码)
     */
    public static <T> JsonContent<T> unAuth() {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), "你没有权限访问此资源！");
    }

    /**
     * 根据响应提示信息生成一个表示资源未授权的响应对象(401响应码)
     *
     * @param message 响应提示信息
     * @return 表示资源未授权的响应对象(401响应码)
     */
    public static <T> JsonContent<T> unAuth(String message) {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message);
    }

    /**
     * 根据响应提示信息和响应数据生成一个表示资源未授权的响应对象(401响应码)
     *
     * @param <T>     响应数据的类型
     * @param message 响应提示信息
     * @param data    响应数据
     * @return 表示资源未授权的响应对象(401响应码)
     */
    public static <T> JsonContent<T> unAuth(String message, T data) {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message, data);
    }

    /**
     * 生成一个默认的表示资源不可用的响应对象(403响应码)
     *
     * @return 表示资源不可用的响应对象(403响应码)
     */
    public static <T> JsonContent<T> notAllow() {
        return new JsonContent<>(HttpStatus.FORBIDDEN.value(), "资源不可用!");
    }

    /**
     * 根据响应提示信息生成表示资源不可用的响应对象(403响应码)
     *
     * @param message 响应提示信息
     * @return 表示资源不可用的响应对象(403响应码)
     */
    public static <T> JsonContent<T> notAllow(String message) {
        return new JsonContent<>(HttpStatus.FORBIDDEN.value(), message);
    }

    /**
     * 生成一个默认的表示资源不存在的响应对象(404响应码)
     *
     * @return 表示资源不存在的响应对象(404响应码)
     */
    public static <T> JsonContent<T> notFound() {
        return new JsonContent<>(HttpStatus.NOT_FOUND.value(), "访问的资源不存在！");
    }

    public boolean isSuccess() {
        return this.code == BreezeModelContext.properties.getSuccessCode();
    }

    public String getId() {
        return TLogContext.getTraceId();
    }
}
