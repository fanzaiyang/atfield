package cn.fanzy.breeze.admin.module.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernameMobileLoginArgs;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.web.model.JsonContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BreezeAdminAuthService {
    /**
     * 登录之前调用
     *
     * @param username
     * @return
     */
    JsonContent<Boolean> doUserPwdLoginBefore(String username);

    JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args);

    JsonContent<SysAccount> doGetCurrentUserInfo();

    JsonContent<SaTokenInfo> doUserMobileLogin(UsernameMobileLoginArgs args);

    JsonContent<Object> doSendUserMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response);
}
