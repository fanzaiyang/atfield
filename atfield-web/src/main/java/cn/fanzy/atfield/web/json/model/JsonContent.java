package cn.fanzy.atfield.web.json.model;

import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.fanzy.atfield.web.configuration.JsonModelAutoConfiguration;
import cn.fanzy.atfield.web.json.property.JsonProperty;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * 统一响应结果
 * <strong>注意：该类已废弃，使用 {@link Json} 代替</strong>
 * @author wanghuifeng
 */
@Deprecated
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class JsonContent<T> {
    private static final JsonProperty PROPERTY = JsonModelAutoConfiguration.getJsonProperty();

    private String id;

    /**
     * 编码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    private String now = DateUtil.now();

    private boolean success = false;

    private Object exData;

    private ShowType showType = ShowType.SILENT;

    public JsonContent() {
    }

    public JsonContent(int code, String message) {
        this.code = code;
        this.message = message;
        this.now = DateUtil.now();
    }

    public JsonContent(int code, String message, ShowType showType) {
        this.code = code;
        this.message = message;
        this.now = DateUtil.now();
        this.showType = showType;
    }

    public JsonContent(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.now = DateUtil.now();
        this.data = data;
    }

    public JsonContent(int code, String message, T data, ShowType showType) {
        this.code = code;
        this.message = message;
        this.now = DateUtil.now();
        this.data = data;
        this.showType = showType;
    }

    /**
     * 成功
     *
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
     * @param <T>    T
     * @return {@link JsonContent} {@link T}
     */
    public static <T> JsonContent<T> success(T object) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultOkCode()),
                PROPERTY.getModel().getDefaultOkMessage(), object);
    }

    /**
     * 失败
     *
     * @param <T> T
     * @return {@link JsonContent} {@link T}
     */
    public static <T> JsonContent<T> error() {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultOkCode()),
                PROPERTY.getModel().getDefaultFailMessage(),
                ShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> error(int errorCode, String errorMessage) {
        return new JsonContent<>(errorCode, errorMessage);
    }


    public static <T> JsonContent<T> error(int errorCode, Exception e) {
        return new JsonContent<>(errorCode, e.getMessage());
    }

    public static <T> JsonContent<T> error(Exception exception) {
        return error(exception.getMessage());
    }

    public static <T> JsonContent<T> error(String errorMessage) {
        return error(new RuntimeException(errorMessage));
    }

    /**
     * 生成一个默认的表示参数有误的响应对象(响应码400)
     *
     * @param <T> T
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam() {
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), "请检查参数！", ShowType.MESSAGE_ERROR);
    }

    /**
     * 根据响应提示信息生成一个表示参数有误的响应对象(响应码400)
     *
     * @param <T>     T
     * @param message 响应提示信息
     * @return 表示参数有误的响应对象(响应码400)
     */
    public static <T> JsonContent<T> badParam(String message) {
        return new JsonContent<>(HttpStatus.BAD_REQUEST.value(), message, ShowType.MESSAGE_ERROR);
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
        return new JsonContent(HttpStatus.BAD_REQUEST.value(), message, data, ShowType.MESSAGE_ERROR);
    }


    public static <T> JsonContent<T> unAuth() {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), "你没有权限访问此资源！", ShowType.NOTIFICATION_WARN);
    }

    /**
     * 根据响应提示信息生成一个表示资源未授权的响应对象(401响应码)
     *
     * @param <T>     T
     * @param message 响应提示信息
     * @return 表示资源未授权的响应对象(401响应码)
     */
    public static <T> JsonContent<T> unAuth(String message) {
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message, ShowType.NOTIFICATION_WARN);
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
        return new JsonContent<>(HttpStatus.UNAUTHORIZED.value(), message, data, ShowType.NOTIFICATION_WARN);
    }


    public static <T> JsonContent<T> notAllow() {
        return new JsonContent<>(HttpStatus.FORBIDDEN.value(), "资源不可用!", ShowType.NOTIFICATION_ERROR);
    }


    public static <T> JsonContent<T> notAllow(String message) {
        return new JsonContent<>(HttpStatus.FORBIDDEN.value(), message, ShowType.NOTIFICATION_ERROR);
    }


    public static <T> JsonContent<T> notFound() {
        return new JsonContent<>(HttpStatus.NOT_FOUND.value(), "访问的资源不存在！", ShowType.NOTIFICATION_WARN);
    }


    public String getId() {
        if (StrUtil.isNotBlank(id)) {
            return id;
        }
        try {
            Class<?> forName = Class.forName("cn.fanzy.atfield.tlog.common.context.TLogContext");
            return TLogContext.getTraceId();
        } catch (Exception ignored) {
        }
        return null;
    }


    public static <T> JsonContent<T> warnMessage(String message) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultFailCode()),
                message, ShowType.MESSAGE_WARN);
    }

    public static <T> JsonContent<T> warnMessage(int code, String message) {
        return new JsonContent<>(code, message, ShowType.MESSAGE_WARN);
    }

    public static <T> JsonContent<T> warnMessage(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ShowType.MESSAGE_WARN);
    }

    public static <T> JsonContent<T> warnModal(String message) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultFailCode()), message, ShowType.MODAL_WARN);
    }

    public static <T> JsonContent<T> warnModal(int code, String message) {
        return new JsonContent<>(code, message, ShowType.MODAL_WARN);
    }

    public static <T> JsonContent<T> warnModal(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ShowType.MODAL_WARN);
    }

    public static <T> JsonContent<T> warnNotification(String message) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultFailCode()), message, ShowType.NOTIFICATION_WARN);
    }

    public static <T> JsonContent<T> warnNotification(int code, String message) {
        return new JsonContent<>(code, message, ShowType.NOTIFICATION_WARN);
    }

    public static <T> JsonContent<T> warnNotification(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ShowType.NOTIFICATION_WARN);
    }


    public static <T> JsonContent<T> errorMessage(String message) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultFailCode()), message, ShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> errorMessage(int code, String message) {
        return new JsonContent<>(code, message, ShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> errorMessage(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ShowType.MESSAGE_ERROR);
    }

    public static <T> JsonContent<T> errorModal(String message) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultFailCode()), message, ShowType.MODAL_ERROR);
    }

    public static <T> JsonContent<T> errorModal(int code, String message) {
        return new JsonContent<>(code, message, ShowType.MODAL_ERROR);
    }

    public static <T> JsonContent<T> errorModal(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ShowType.MODAL_ERROR);
    }

    public static <T> JsonContent<T> errorNotification(String message) {
        return new JsonContent<>(Integer.parseInt(PROPERTY.getModel().getDefaultFailCode()), message, ShowType.NOTIFICATION_ERROR);
    }

    public static <T> JsonContent<T> errorNotification(int code, String message) {
        return new JsonContent<>(code, message, ShowType.NOTIFICATION_ERROR);
    }

    public static <T> JsonContent<T> errorNotification(int code, String message, T data) {
        return new JsonContent<>(code, message, data, ShowType.NOTIFICATION_ERROR);
    }

    public boolean isSuccess() {
        return StrUtil.equals(getCode() + "", PROPERTY.getModel().getDefaultOkCode());
    }
}
