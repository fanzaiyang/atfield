package cn.fanzy.breeze.admin.module.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.web.model.JsonContent;

public interface BreezeAdminAuthService {
    /**
     * 登录之前调用
     *
     * @param username
     * @return
     */
    JsonContent<Object> doUserPwdLoginBefore(String username);

    JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args);

    JsonContent<SaTokenInfo> doUserWxLogin(UsernamePasswordLoginArgs args);

    JsonContent<SysAccount> doGetCurrentUserInfo();

}
