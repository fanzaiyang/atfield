package cn.fanzy.atfield.satoken.login.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * 登录验证码服务
 *
 * @author fanzaiyang
 * @date 2024/01/16
 */
public interface LoginCaptchaService {

    /**
     * 验证码验证
     *
     * @param requestParams 请求参数
     * @param request       请求
     */
    void check(Map<String, Object> requestParams, HttpServletRequest request);
}
