package cn.fanzy.atfield.satoken.login.service.impl;

import cn.fanzy.atfield.captcha.CaptchaService;
import cn.fanzy.atfield.satoken.login.property.LoginProperty;
import cn.fanzy.atfield.satoken.login.service.LoginCaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginCaptchaServiceImpl implements LoginCaptchaService {
    private final CaptchaService captchaService;
    private final LoginProperty property;

    @Override
    public void check(Map<String, Object> requestParams, HttpServletRequest request) {
        captchaService.verify(property.getCaptchaType(), request);
    }
}
