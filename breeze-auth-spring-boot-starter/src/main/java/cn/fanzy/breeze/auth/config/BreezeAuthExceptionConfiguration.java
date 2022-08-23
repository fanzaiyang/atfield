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
public class BreezeAuthExceptionConfiguration {


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotLoginException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotLoginException异常：{}", ssid, e.getMessage()), e);
        return response;
    }
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotBasicAuthException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotBasicAuthException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotBasicAuthException异常：{}", ssid, e.getMessage()), e);
        return response;
    }
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DisableLoginException.class)
    public Object handleRuntimeException(HttpServletRequest request, DisableLoginException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(),ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到DisableLoginException异常：{}", ssid, e.getMessage()), e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotRoleException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotRoleException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.FORBIDDEN.value(), e.getMessage(),ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotRoleException异常：{}", ssid, e.getMessage()), e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotPermissionException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotPermissionException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.FORBIDDEN.value(), e.getMessage(),ssid);
        log.error(StrUtil.format("「微风组件」请求{},请求失败,拦截到NotPermissionException异常：{}", ssid, e.getMessage()), e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotSafeException.class)
    public Object handleRuntimeException(HttpServletRequest request, NotSafeException e) {
        String ssid = this.getRequestId(request);
        Response response = new Response(HttpStatus.PRECONDITION_REQUIRED.value(), e.getMessage(),ssid);
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


        public Response(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Auth鉴权异常拦截> 相关的配置");
    }

}