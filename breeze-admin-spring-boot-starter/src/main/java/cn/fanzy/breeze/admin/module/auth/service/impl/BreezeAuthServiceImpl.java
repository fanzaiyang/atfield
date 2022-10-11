package cn.fanzy.breeze.admin.module.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAuthService;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.service.SqlToyCRUDService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
@AllArgsConstructor
public class BreezeAuthServiceImpl implements BreezeAuthService {

    private final SqlToyLazyDao sqlToyLazyDao;
    private final SqlToyCRUDService sqlToyCRUDService;
    @Override
    public JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args) {
        SysAccount account = sqlToyCRUDService.load(SysAccount.builder().nickName(args.getUsername()).build());
        return null;
    }

    @Override
    public JsonContent<SaTokenInfo> doUserWxLogin(UsernamePasswordLoginArgs args) {
        return null;
    }

    @Override
    public JsonContent<SysAccount> doGetCurrentUserInfo() {
        return null;
    }
}
