package cn.fanzy.breeze.auth.config;

import cn.dev33.satoken.exception.*;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


/**
 * 全局异常捕获自动配置授权相关
 *
 * @author fanzaiyang
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@ResponseBody
@ConditionalOnClass(SaTokenException.class)
@PropertySource(value = {"classpath:application-satoken.properties"})
public class BreezeAuthExceptionConfiguration {


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotLoginException e) {
        String ssid = this.getRequestId(request);
        String type = e.getType();
        String message = "";
        if (NotLoginException.NOT_TOKEN.equals(type)) {
            message = "未提供token，请重新登录！";
        } else if (NotLoginException.INVALID_TOKEN.equals(type)) {
            message = "令牌无效，请重新登录！";
        } else if (NotLoginException.TOKEN_TIMEOUT.equals(type)) {
            message = "登录已过期，请重新登录！";
        } else if (NotLoginException.BE_REPLACED.equals(type)) {
            message = "当前账号已被其他客户端登录！";
        } else if (NotLoginException.KICK_OUT.equals(type)) {
            message = "您已被强制下线！";
        } else {
            message = e.getMessage();
        }

        Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotLoginException异常：{}", ssid, message), e);
        response.setErrorShowType(ErrorShowType.MODAL_ERROR);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotBasicAuthException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotBasicAuthException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotBasicAuthException异常：{}", ssid, e.getMessage()), e);
        response.setErrorShowType(ErrorShowType.MESSAGE_ERROR);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DisableServiceException.class)
    public Object handleRuntimeException(HttpServletRequest request, DisableServiceException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到DisableServiceException异常：{}", ssid, e.getMessage()), e);
        response.setErrorShowType(ErrorShowType.MESSAGE_ERROR);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotRoleException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotRoleException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.FORBIDDEN.value(), e.getMessage(), ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotRoleException异常：{}", ssid, e.getMessage()), e);
        response.setErrorShowType(ErrorShowType.MESSAGE_ERROR);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotPermissionException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotPermissionException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.FORBIDDEN.value(), e.getMessage(), ssid);
        response.setErrorShowType(ErrorShowType.MESSAGE_ERROR);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotPermissionException异常：{}", ssid, e.getMessage()), e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotSafeException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotSafeException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.PRECONDITION_REQUIRED.value(), e.getMessage(), ssid);
        response.setErrorShowType(ErrorShowType.SILENT);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotSafeException异常：{}", ssid, e.getMessage()), e);
        return response;
    }

    /**
     * 获取请求的id
     *
     * @param request HttpServletRequest
     * @return 请求的ID
     */
    private String getRequestId(HttpServletRequest request) {
        return StrUtil.format("[{}][{}]", request.getMethod(), request.getRequestURI());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String id = IdUtil.simpleUUID();

        private int code;

        private String message;

        private String data;

        private String now = DateUtil.now();

        private boolean success = false;
        private ErrorShowType errorShowType = ErrorShowType.SILENT;


        public Response(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }
        public Response(int code, String message, String data,ErrorShowType errorShowType) {
            this.code = code;
            this.message = message;
            this.data = data;
            this.errorShowType=errorShowType;
        }
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Auth鉴权异常拦截> 相关的配置");
    }


    public static enum ErrorShowType{
        /**
         * 静默，不显示
         */
        SILENT,
        /**
         * 显示为警告Message
         */
        MESSAGE_WARN,
        /**
         * 显示为错误Message
         */
        MESSAGE_ERROR,
        /**
         * 显示为弹窗警告
         */
        MODAL_WARN,
        /**
         * 显示为弹窗错误
         */
        MODAL_ERROR,
        /**
         * 显示为通知警告NOTIFICATION
         */
        NOTIFICATION_WARN,
        /**
         * 显示为通知错误NOTIFICATION
         */
        NOTIFICATION_ERROR;
    }
}