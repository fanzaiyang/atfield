package cn.fanzy.breeze.admin.module.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.web.model.JsonContent;

public interface BreezeAuthService {
    /**
     * 登录之前调用
     *
     * @param clientId
     * @return
     */
    JsonContent<Object> doUserPwdLoginBefore(String clientId);

    JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args);

    JsonContent<SaTokenInfo> doUserWxLogin(UsernamePasswordLoginArgs args);

    JsonContent<SysAccount> doGetCurrentUserInfo();

}
