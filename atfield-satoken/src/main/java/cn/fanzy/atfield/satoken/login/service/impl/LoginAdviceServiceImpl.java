package cn.fanzy.atfield.satoken.login.service.impl;

import cn.fanzy.atfield.cache.CacheService;
import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.satoken.login.exception.LoginOverRetryException;
import cn.fanzy.atfield.satoken.login.model.LoginAopInfoDto;
import cn.fanzy.atfield.satoken.login.service.LoginAdviceService;
import cn.fanzy.atfield.satoken.login.service.LoginCaptchaService;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginAdviceServiceImpl implements LoginAdviceService {

    private final CacheService cacheService;
    private final LoginCaptchaService loginCaptchaService;

    @Override
    public void check(LoginAopInfoDto dto) {
        // 检查是否已经超过错误次数
        if (dto.getErrorCount() >= dto.getRetryCount()) {
            log.error("登录失败，错误次数超过最大次数，登录失败！");
            String message = StrUtil.format("登录失败，登录错误次数过多，请在{}后重试。", dto.getUnlockTime());
            throw new LoginOverRetryException(message);
        }
        if (dto.isShowCaptcha()) {
            // todo 执行验证码验证码逻辑
            HttpServletRequest request = SpringUtils.getRequest();
            Map<String, Object> requestParams = SpringUtils.getRequestParams(request);
            loginCaptchaService.check(requestParams, request);
        }
    }

    @Override
    public void error(LoginAopInfoDto dto) {
        cacheService.put(dto.getStorageKey(), dto);
    }

    @Override
    public void success(LoginAopInfoDto dto) {
        cacheService.remove(dto.getStorageKey());
    }
}
