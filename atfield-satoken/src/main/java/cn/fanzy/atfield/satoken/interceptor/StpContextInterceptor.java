package cn.fanzy.atfield.satoken.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.atfield.satoken.context.StpContext;
import cn.fanzy.atfield.satoken.utils.SaSessionUtils;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * STP 上下文拦截器
 *
 * @author fanzaiyang
 * @date 2024/12/02
 */
@Slf4j
public class StpContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (StpUtil.isLogin()) {
                StpContext.putLoginId(StpUtil.getLoginIdAsString());
                String loginName = SaSessionUtils.getStr(SaSessionUtils.SA_LOGIN_NAME);
                if (StrUtil.isBlank(loginName)) {
                    StpContext.putLoginName(SaSessionUtils.getStr(SaSessionUtils.SA_LOGIN_NAME));
                }
            }
            if (StrUtil.isBlank(StpContext.getLoginId())) {
                StpContext.putLoginId("anonymous");
            }
            if (StrUtil.isBlank(StpContext.getLoginName())) {
                StpContext.putLoginName("匿名用户");
            }
        } catch (Exception e) {
            log.warn("StpContextInterceptor preHandle error:{}", e.getMessage());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StpContext.removeLoginId();
        StpContext.removeLoginName();
    }
}
