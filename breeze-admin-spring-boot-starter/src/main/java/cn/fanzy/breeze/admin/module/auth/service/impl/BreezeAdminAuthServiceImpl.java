package cn.fanzy.breeze.admin.module.auth.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.admin.module.auth.args.UsernameMobileLoginArgs;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.password.PasswordEncoder;
import cn.fanzy.breeze.web.password.bcrypt.BCryptPasswordEncoder;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BreezeAdminAuthServiceImpl implements BreezeAdminAuthService {

    private final SqlToyHelperDao sqlToyHelperDao;
    private final BreezeSafeService breezeSafeService;

    @Override
    public JsonContent<Boolean> doUserPwdLoginBefore(String username) {
        // 判断是否需要显示验证码
        boolean showCode = breezeSafeService.isShowCode(username);
        return JsonContent.success(showCode);
    }

    @Override
    public JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args) {
        SysAccount account = sqlToyHelperDao.findOne(Wrappers.lambdaWrapper(SysAccount.class)
                .and(i -> i.eq(SysAccount::getUsername, args.getUsername())
                        .or()
                        .eq(SysAccount::getTelnum, args.getUsername())
                        .or()
                        .eq(SysAccount::getWorkTelnum, args.getUsername()))
                .eq(IBaseEntity::getDelFlag, 0));
        Assert.notNull(account, "该账号不存在!");
        Assert.isTrue(account.getStatus() == 1, "该账号已被禁用！");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Assert.isTrue(passwordEncoder.matches(args.getPassword(), account.getPassowrd()), "密码错误！");
        StpUtil.login(account.getId());
        return JsonContent.success(StpUtil.getTokenInfo());
    }

    @Override
    public JsonContent<SysAccount> doGetCurrentUserInfo() {
        String loginId = StpUtil.getLoginIdAsString();
        List<SysAccount> accountList = sqlToyHelperDao.loadByIds(SysAccount.class, loginId);
        if (CollUtil.isEmpty(accountList)) {
            throw new NotLoginException(StrUtil.format("未找到ID为「{}」的用户！", loginId), StpUtil.TYPE, NotLoginException.DEFAULT_MESSAGE);
        }
        return JsonContent.success(accountList.get(0));
    }


    @Override
    public JsonContent<SaTokenInfo> doUserMobileLogin(UsernameMobileLoginArgs args) {
        return null;
    }

    @Override
    public JsonContent<Object> doSendUserMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        BreezeCodeProcessor processor = SpringUtils.getBean(BreezeCodeProcessor.class);
        processor.createAndSend(new ServletWebRequest(request, response), BreezeCodeType.SMS);
        return JsonContent.success();
    }

}
