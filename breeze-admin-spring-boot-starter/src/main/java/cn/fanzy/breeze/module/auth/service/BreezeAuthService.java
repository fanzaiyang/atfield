package cn.fanzy.breeze.module.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.web.model.JsonContent;

public interface BreezeAuthService {
    JsonContent<SaTokenInfo> doUserPwdLogin();

    JsonContent<SaTokenInfo> doUserWxLogin();

}
