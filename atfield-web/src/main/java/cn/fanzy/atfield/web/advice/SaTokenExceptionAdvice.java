package cn.fanzy.atfield.web.advice;

import cn.dev33.satoken.exception.*;
import cn.fanzy.atfield.core.utils.ExceptionUtil;
import cn.fanzy.atfield.web.json.model.Json;
import cn.fanzy.atfield.web.json.model.ShowType;
import cn.fanzy.atfield.web.json.property.JsonProperty;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JsonProperty.class)
@AutoConfigureBefore(GlobalExceptionAdvice.class)
@ConditionalOnClass(SaTokenException.class)
public class SaTokenExceptionAdvice {

    private final JsonProperty property;

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ApiDisabledException.class)
    public Object handleApiDisabledException(HttpServletRequest request, ApiDisabledException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), "API已被禁用!");
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},ApiDisabledException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BackResultException.class)
    public Object handleBackResultException(HttpServletRequest request, BackResultException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},BackResultException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DisableServiceException.class)
    public Object handleDisableServiceException(HttpServletRequest request, DisableServiceException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},DisableServiceException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(InvalidContextException.class)
    public Object handleInvalidContextException(HttpServletRequest request, InvalidContextException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},InvalidContextException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotHttpBasicAuthException.class)
    public Object handleNotBasicAuthException(HttpServletRequest request, NotHttpBasicAuthException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>("401", e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotBasicAuthException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotImplException.class)
    public Object handleNotImplException(HttpServletRequest request, NotImplException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotImplException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotLoginException.class)
    public Object handleNotLoginException(HttpServletRequest request, NotLoginException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>("401", e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotLoginException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotPermissionException.class)
    public Object handleNotLoginException(HttpServletRequest request, NotPermissionException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(e.getPermission(), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotPermissionException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotRoleException.class)
    public Object handleNotRoleException(HttpServletRequest request, NotRoleException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotRoleException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotSafeException.class)
    public Object handleNotSafeException(HttpServletRequest request, NotSafeException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotSafeException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotWebContextException.class)
    public Object handleNotWebContextException(HttpServletRequest request, NotWebContextException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},NotWebContextException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RequestPathInvalidException.class)
    public Object handleRequestPathInvalidException(HttpServletRequest request, RequestPathInvalidException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},RequestPathInvalidException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SaJsonConvertException.class)
    public Object handleSaJsonConvertException(HttpServletRequest request, SaJsonConvertException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},SaJsonConvertException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SaSignException.class)
    public Object handleSaSignException(HttpServletRequest request, SaSignException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},SaSignException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SameTokenInvalidException.class)
    public Object handleSameTokenInvalidException(HttpServletRequest request, SameTokenInvalidException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},SameTokenInvalidException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
        return response;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(StopMatchException.class)
    public Object handleStopMatchException(HttpServletRequest request, StopMatchException e) {
        String ssid = this.getRequestId(request);
        Json<String> response = new Json<>(String.valueOf(e.getCode()), e.getMessage());
        response.setShowType(ShowType.NOTIFICATION_ERROR);
        log.error(StrUtil.format("「全局异常」请求{},StopMatchException,失败的原因为：{}", ssid, e.getMessage())
                , e);
        setErrorStacks(response, e);
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

    private void setErrorStacks(Json<?> response, Exception e) {
        if (property.getModel().isEnableErrorStack()) {
            response.setErrorStacks(ExceptionUtil.getErrorStackMessage(e, property.getModel().getErrorStackSize()));
        }
    }

    @PostConstruct
    public void init() {
        log.debug("开启 <SaToken异常拦截> 相关的配置。");
    }
}
