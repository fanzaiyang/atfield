package cn.fanzy.breeze.admin.module.auth.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.admin.module.auth.args.UsernameMobileLoginArgs;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.admin.module.auth.vo.CurrentUserInfoVo;
import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.entity.SysRole;
import cn.fanzy.breeze.core.utils.TreeUtils;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.code.model.BreezeImageCode;
import cn.fanzy.breeze.web.code.processor.BreezeCodeProcessor;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.password.PasswordEncoder;
import cn.fanzy.breeze.web.password.bcrypt.BCryptPasswordEncoder;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.MapKit;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 微风impl管理员身份验证服务
 *
 * @author fanzaiyang
 * @date 2022-11-07
 */
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
        Assert.isTrue(passwordEncoder.matches(args.getPassword(), account.getPassword()), "密码错误！");
        StpUtil.login(account.getId());
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysAccount.class)
                .set(SysAccount::getLastLoginIp, SpringUtils.getClientIp())
                .set(SysAccount::getLastLoginDate, new Date())
                .eq(SysAccount::getId, account.getId()));
        StpUtil.getSession().set("userInfo", account);
        return JsonContent.success(StpUtil.getTokenInfo());
    }

    @Override
    public JsonContent<CurrentUserInfoVo> doGetCurrentUserInfo() {
        SaSession session = StpUtil.getSession();
        String loginId = StpUtil.getLoginIdAsString();
        Object o = session.get(loginId);
        if (o instanceof CurrentUserInfoVo) {
            return JsonContent.success((CurrentUserInfoVo) o);
        }
        List<SysAccount> accountList = sqlToyHelperDao.loadByIds(SysAccount.class, loginId);
        if (CollUtil.isEmpty(accountList)) {
            throw new NotLoginException(StrUtil.format("未找到ID为「{}」的用户！", loginId), StpUtil.TYPE, NotLoginException.DEFAULT_MESSAGE);
        }
        CurrentUserInfoVo currentUserInfoVo = BeanUtil.copyProperties(accountList.get(0), CurrentUserInfoVo.class);
        // 查询当前登录人的角色
        String sql = "SELECT t.* FROM sys_role t INNER JOIN sys_account_role ar on ar.role_id=t.id and ar.del_flag=0 and ar.account_id=:accountId WHERE t.del_flag=0";
        List<SysRole> roleList = sqlToyHelperDao.findBySql(sql, MapKit.map("accountId", loginId), SysRole.class);
        currentUserInfoVo.setRoleList(roleList);
        session.set(loginId, currentUserInfoVo);
        return JsonContent.success(currentUserInfoVo);
    }


    @Override
    public JsonContent<SaTokenInfo> doUserMobileLogin(UsernameMobileLoginArgs args) {
        SysAccount account = sqlToyHelperDao.findOne(Wrappers.lambdaWrapper(SysAccount.class)
                .and(i -> i.eq(SysAccount::getTelnum, args.getMobile())
                        .or()
                        .eq(SysAccount::getWorkTelnum, args.getMobile()))
                .eq(SysAccount::getDelFlag, 0));
        Assert.notNull(account, "该账号不存在!");
        Assert.isTrue(account.getStatus() == 1, "该账号已被禁用！");
        StpUtil.login(account.getId());
        StpUtil.getSession().set("userInfo", account);
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysAccount.class)
                .set(SysAccount::getLastLoginIp, SpringUtils.getClientIp())
                .set(SysAccount::getLastLoginDate, new Date())
                .eq(SysAccount::getId, account.getId()));
        return JsonContent.success(StpUtil.getTokenInfo());
    }

    @Override
    public JsonContent<Object> doSendUserMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        BreezeCodeProcessor processor = SpringUtils.getBean(BreezeCodeProcessor.class);
        processor.createAndSend(new ServletWebRequest(request, response), BreezeCodeType.SMS);
        return JsonContent.success();
    }

    @Override
    public JsonContent<String> doSendUserImageCode(HttpServletRequest request, HttpServletResponse response) {
        BreezeCodeProcessor processor = SpringUtils.getBean(BreezeCodeProcessor.class);
        BreezeImageCode code = (BreezeImageCode) processor.create(new ServletWebRequest(request, response), BreezeCodeType.IMAGE);
        return JsonContent.success(code.getImageBase64());
    }

    @Override
    public JsonContent<List<SysMenu>> doGetCurrentMenu() {
        // 查询当前用户的菜单
        String sql = "SELECT m.* FROM sys_menu m INNER JOIN sys_role_menu rm ON m.id = rm.menu_id INNER JOIN sys_account_role ar ON rm.role_id = ar.role_id AND ar.account_id = :accountId";
        List<SysMenu> menuList = sqlToyHelperDao.findBySql(sql, MapKit.map("accountId", StpUtil.getLoginId()), SysMenu.class);
        return JsonContent.success(menuList);
    }

    @Override
    public JsonContent<List<Tree<String>>> doGetCurrentMenuTree() {
        JsonContent<List<SysMenu>> menu = doGetCurrentMenu();
        return JsonContent.success(TreeUtils.buildTree(menu.getData()));
    }
}
