package cn.fanzy.breeze.admin.module.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAuthService;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.password.PasswordEncoder;
import cn.fanzy.breeze.web.password.bcrypt.BCryptPasswordEncoder;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.service.SqlToyCRUDService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BreezeAuthServiceImpl implements BreezeAuthService {

    private final SqlToyHelperDao sqlToyHelperDao;
    private final SqlToyCRUDService sqlToyCRUDService;
    private final BreezeSafeService breezeSafeService;

    @Override
    public JsonContent<Object> doUserPwdLoginBefore(String clientId) {
        return null;
    }

    @Override
    public JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args) {
        SysAccount account = sqlToyHelperDao.findOne(Wrappers.lambdaWrapper(SysAccount.class)
                .and(i -> i.eq(SysAccount::getUsername, args.getUsername())
                        .or()
                        .eq(SysAccount::getTelnum, args.getUsername())
                        .or()
                        .eq(SysAccount::getWorkTelnum, args.getUsername()))
                .eq(IBaseEntity::getDelFlag, 0)
                .last("limit 1"));
        Assert.notNull(account, "该账号不存在!");
        Assert.isTrue(account.getStatus() == 1, "该账号已被禁用！");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Assert.isTrue(passwordEncoder.matches(args.getPassword(), account.getPassowrd()), "密码错误！");
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
