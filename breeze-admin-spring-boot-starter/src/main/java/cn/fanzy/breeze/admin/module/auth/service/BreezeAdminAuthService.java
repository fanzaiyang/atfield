package cn.fanzy.breeze.admin.module.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernameMobileLoginArgs;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.vo.ClientEnvVo;
import cn.fanzy.breeze.admin.module.auth.vo.CurrentUserInfoVo;
import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author fanzaiyang
 */
public interface BreezeAdminAuthService {
    /**
     * 登录之前调用
     *
     * @param username string
     * @return JsonContent
     */
    JsonContent<Boolean> doUserPwdLoginBefore(String username);

    JsonContent<SaTokenInfo> doUserPwdLogin(UsernamePasswordLoginArgs args);

    JsonContent<CurrentUserInfoVo> doGetCurrentUserInfo();

    JsonContent<SaTokenInfo> doUserMobileLogin(UsernameMobileLoginArgs args);

    JsonContent<Object> doSendUserMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response);
    JsonContent<String> doSendUserImageCode(HttpServletRequest request, HttpServletResponse response);

    JsonContent<List<SysMenu>> doGetCurrentMenu();

    JsonContent<List<Tree<String>>> doGetCurrentMenuTree();


    JsonContent<ClientEnvVo> getClientEnv(HttpServletRequest request);
}
