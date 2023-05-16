package cn.fanzy.breeze.web.model;

import cn.fanzy.breeze.web.model.context.BreezeModelContext;
import cn.fanzy.breeze.web.model.enums.ErrorShowType;
import cn.hutool.core.date.DateUtil;
import com.yomahub.tlog.context.TLogContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

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

    private ErrorShowType errorShowType = ErrorShowType.MESSAGE_ERROR;

    public JsonContent() {
    }

    public JsonContent(int code, String message) {
        this.code = code;
        this.message = message;
        this.success = code == BreezeModelContext.properties.getSuccessCode();
        this.now = DateUtil.now();
    }

    public JsonContent(int code, String message, ErrorShowType showType) {
        this.code = code;
        this.message = message;
        this.success = code == BreezeModelContext.properties.getSuccessCode();
        this.now = DateUtil.now();
        this.errorShowType = showType;
    }

    public JsonContent(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = code == BreezeModelContext.properties.getSuccessCode();
        this.now = DateUtil.now();
        this.data = data;
    }

    public JsonContent(int code, String message, T data, ErrorShowType showType) {
        this.code = code;
        this.message = message;
        this.success = code == BreezeModelContext.properties.getSuccessCode();
        this.now = DateUtil.now();
        this.data = data;
        this.errorShowType = showType;
    }

    /**
     * 成功
     * @param <T> T
     * @return JsonContent
     */
    public static <T> JsonContent<T> success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param object 对象
     * @param <T> T
     * @return {@link JsonContent} {@link T}
     */
    public static <T> JsonContent<T> success(T object) {
        return new JsonContent<>(BreezeModelContext.properties.getSuccessCode(), BreezeModelContext.properties.getSuccessMessage(), object);
    }

    /**
     * 失败
     * @param <T> T
     * @return {@link JsonContent} {@link T}
     */
    public static <T> JsonContent<T> error() {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), BreezeModelContext.properties.getErrorMessage(), ErrorShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> error(int errorCode, String errorMessage) {
        return new JsonContent<>(errorCode, errorMessage, ErrorShowType.MESSAGE_ERROR);
    }


    public static <T> JsonContent<T> error(int errorCode, Exception e) {
        return new JsonContent<>(errorCode, e.getMessage(), ErrorShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> error(Exception exception) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), exception.getMessage(), ErrorShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> error(String errorMessage) {
        return error(new RuntimeException(errorMessage));
    }

    /**
     * 生成一个默认的表示参数有误的响应对象(响应码400)
     * @param <T> T
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam() {
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), "请检查参数！", ErrorShowType.MESSAGE_ERROR);
    }

    /**
     * 根据响应提示信息生成一个表示参数有误的响应对象(响应码400)
     *
     * @param <T> T
     * @param message 响应提示信息
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam(String message) {
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), message, ErrorShowType.MESSAGE_ERROR);
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
        return new JsonContent(HttpStatus.BAD_REQUEST.value(), message, data, ErrorShowType.MESSAGE_ERROR);
    }


    public static <T> JsonContent<T> unAuth() {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), "你没有权限访问此资源！", ErrorShowType.NOTIFICATION_WARN);
    }

    /**
     * 根据响应提示信息生成一个表示资源未授权的响应对象(401响应码)
     * @param <T> T
     * @param message 响应提示信息
     * @return 表示资源未授权的响应对象(401响应码)
     */
    public static <T> JsonContent<T> unAuth(String message) {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message, ErrorShowType.NOTIFICATION_WARN);
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
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message, data, ErrorShowType.NOTIFICATION_WARN);
    }


    public static <T> JsonContent<T> notAllow() {
        return new JsonContent<>(HttpStatus.FORBIDDEN.value(), "资源不可用!", ErrorShowType.NOTIFICATION_ERROR);
    }


    public static <T> JsonContent<T> notAllow(String message) {
        return new JsonContent<>(HttpStatus.FORBIDDEN.value(), message, ErrorShowType.NOTIFICATION_ERROR);
    }


    public static <T> JsonContent<T> notFound() {
        return new JsonContent<>(HttpStatus.NOT_FOUND.value(), "访问的资源不存在！", ErrorShowType.NOTIFICATION_WARN);
    }

    public boolean isSuccess() {
        return this.code == BreezeModelContext.properties.getSuccessCode();
    }

    public String getId() {
        return TLogContext.getTraceId();
    }


    public static <T> JsonContent<T> warnMessage(String message) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), message, ErrorShowType.MESSAGE_WARN);
    }

    public static <T> JsonContent<T> warnMessage(int code, String message) {
        return new JsonContent<>(code, message, ErrorShowType.MESSAGE_WARN);
    }

    public static <T> JsonContent<T> warnMessage(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ErrorShowType.MESSAGE_WARN);
    }

    public static <T> JsonContent<T> warnModal(String message) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), message, ErrorShowType.MODAL_WARN);
    }

    public static <T> JsonContent<T> warnModal(int code, String message) {
        return new JsonContent<>(code, message, ErrorShowType.MODAL_WARN);
    }

    public static <T> JsonContent<T> warnModal(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ErrorShowType.MODAL_WARN);
    }

    public static <T> JsonContent<T> warnNotification(String message) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), message, ErrorShowType.NOTIFICATION_WARN);
    }

    public static <T> JsonContent<T> warnNotification(int code, String message) {
        return new JsonContent<>(code, message, ErrorShowType.NOTIFICATION_WARN);
    }

    public static <T> JsonContent<T> warnNotification(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ErrorShowType.NOTIFICATION_WARN);
    }


    public static <T> JsonContent<T> errorMessage(String message) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), message, ErrorShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> errorMessage(int code, String message) {
        return new JsonContent<>(code, message, ErrorShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> errorMessage(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ErrorShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> errorModal(String message) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), message, ErrorShowType.MODAL_ERROR);
    }

    public static <T> JsonContent<T> errorModal(int code, String message) {
        return new JsonContent<>(code, message, ErrorShowType.MODAL_ERROR);
    }

    public static <T> JsonContent<T> errorModal(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ErrorShowType.MODAL_ERROR);
    }

    public static <T> JsonContent<T> errorNotification(String message) {
        return new JsonContent<>(BreezeModelContext.properties.getErrorCode(), message, ErrorShowType.NOTIFICATION_ERROR);
    }

    public static <T> JsonContent<T> errorNotification(int code, String message) {
        return new JsonContent<>(code, message, ErrorShowType.NOTIFICATION_ERROR);
    }

    public static <T> JsonContent<T> errorNotification(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ErrorShowType.NOTIFICATION_ERROR);
    }
}
