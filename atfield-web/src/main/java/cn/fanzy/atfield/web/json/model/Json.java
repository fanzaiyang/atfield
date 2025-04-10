package cn.fanzy.atfield.web.json.model;

import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.fanzy.atfield.web.configuration.JsonModelAutoConfiguration;
import cn.fanzy.atfield.web.json.property.JsonProperty;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全局响应对象
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Getter
@Setter
public class Json<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1222584965768862227L;


    private static final JsonProperty PROPERTY = JsonModelAutoConfiguration.getJsonProperty();


    /**
     * 链路id
     */
    private String traceId;
    /**
     * 错误码
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 当前时间
     */
    private LocalDateTime now;

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 异常返回前台的类型
     * @see ShowType
     */
    private ShowType showType = ShowType.SILENT;

    /**
     * 错误堆栈
     */
    private Object errorStacks;

    public Json() {
    }

    public Json(String traceId, String code, String message, T data) {
        this.traceId = traceId;
        this.code = code;
        this.message = message;
        this.data = data;
        this.now = LocalDateTime.now();
    }

    public Json(String traceId, String code, String message, T data, ShowType showType) {
        this.traceId = traceId;
        this.code = code;
        this.message = message;
        this.data = data;
        this.now = LocalDateTime.now();
        this.showType = showType;
    }

    public Json(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Json(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Json(String code, String message, T data, ShowType showType) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.showType = showType;
    }

    /**
     * 成功
     *
     * @return {@link Json}<{@link T}>
     */
    public static <T> Json<T> ok() {
        return ok(null);
    }

    public static <T> Json<T> ok(String message) {
        return ok(null, message, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Json}<{@link T}>
     */
    public static <T> Json<T> ok(T data) {
        return ok(null, null, data);
    }

    /**
     * 返回成功数据
     *
     * @param data 数据
     * @return {@link Json}<{@link T}>
     */
    public static <T> Json<T> data(T data) {
        return ok(null, null, data);
    }

    public static <T> Json<T> ok(String message, T data) {
        return ok(null, message, data);
    }

    public static <T> Json<T> ok(String code, String message, T data) {
        return new Json<>(StrUtil.blankToDefault(code, PROPERTY.getModel().getDefaultOkCode()),
                StrUtil.blankToDefault(message, PROPERTY.getModel().getDefaultOkMessage()),
                data);
    }

    public static <T> Json<T> success() {
        return success(null);
    }

    public static <T> Json<T> success(String message) {
        return success(null, message, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link Json}<{@link T}>
     */
    public static <T> Json<T> success(T data) {
        return success(null, null, data);
    }

    /**
     * 成功
     *
     * @param message 消息
     * @param data    数据
     * @return {@link Json}<{@link T}>
     */
    public static <T> Json<T> success(String message, T data) {
        return success(null, message, data);
    }

    /**
     * 成功
     *
     * @param code    编码
     * @param message 消息
     * @param data    数据
     * @return {@link Json}<{@link T}>
     */
    public static <T> Json<T> success(String code, String message, T data) {
        return new Json<>(StrUtil.blankToDefault(code, PROPERTY.getModel().getDefaultOkCode()),
                StrUtil.blankToDefault(message, PROPERTY.getModel().getDefaultOkMessage()),
                data);
    }

    public static <T> Json<T> fail() {
        return fail(null);
    }

    public static <T> Json<T> fail(String message) {
        return fail(null, message);
    }

    public static <T> Json<T> fail(String message, T data) {
        return fail(null, message, data);
    }

    public static <T> Json<T> fail(String code, String message) {
        return fail(code, message, null);
    }

    public static <T> Json<T> fail(T data) {
        return fail(null, null, data);
    }

    public static <T> Json<T> fail(String code, String message, T data) {
        return new Json<>(StrUtil.blankToDefault(code, PROPERTY.getModel().getDefaultFailCode()),
                StrUtil.blankToDefault(message, PROPERTY.getModel().getDefaultFailMessage()),
                data);
    }

    public static <T> Json<T> error() {
        return fail(null);
    }

    public static <T> Json<T> error(String message) {
        return error(null, message);
    }

    public static <T> Json<T> error(String message, T data) {
        return error(null, message, data);
    }

    public static <T> Json<T> error(String code, String message) {
        return error(code, message, null);
    }

    public static <T> Json<T> error(T data) {
        return error(null, null, data);
    }

    public static <T> Json<T> error(String code, String message, T data) {
        return new Json<>(StrUtil.blankToDefault(code, PROPERTY.getModel().getDefaultFailCode()),
                StrUtil.blankToDefault(message, PROPERTY.getModel().getDefaultFailMessage()),
                data);
    }

    public String getTraceId() {
        if (StrUtil.isNotBlank(traceId)) {
            return traceId;
        }
        try {
            Class<?> forName = Class.forName("cn.fanzy.atfield.tlog.common.context.TLogContext");
            return TLogContext.getTraceId();
        } catch (Exception ignored) {
        }
        return null;
    }

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public boolean isSuccess() {
        return StrUtil.equals(getCode(), PROPERTY.getModel().getDefaultOkCode());
    }
}
