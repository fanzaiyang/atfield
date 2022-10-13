package cn.fanzy.breeze.admin.module.auth.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.admin.module.auth.args.UsernameMobileLoginArgs;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.web.code.annotation.BreezeCodeChecker;
import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import cn.hutool.core.lang.tree.Tree;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "「微风组件」授权登录")
@ApiSupport(author = "微风组件", order = 991001)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix}")
public class BreezeAdminAuthController {

    private final BreezeAdminAuthService breezeAuthService;

    @ApiOperation(value = "登录前", notes = "登录前调用此接口，用于判断是否需要显示验证码。true显示，false不显示")
    @ApiOperationSupport(order = 10)
    @ApiImplicitParam(name = "username", value = "登录名")
    @GetMapping("/login/before")
    public JsonContent<Boolean> doUserPwdLoginBefore(String username) {
        return breezeAuthService.doUserPwdLoginBefore(username);
    }

    @BreezeSafe
    @ApiOperation(value = "用户名密码登录")
    @ApiOperationSupport(order = 20)
    @PostMapping("/login/pwd")
    public JsonContent<SaTokenInfo> doUserPwdLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserPwdLogin(args);
    }

    @BreezeCodeChecker(BreezeCodeType.SMS)
    @ApiOperation(value = "发送手机验证码")
    @ApiOperationSupport(order = 30)
    @ApiImplicitParam(name = "mobile", value = "登录名")
    @GetMapping("/login/mobile/code/send")
    public JsonContent<Object> doSendUserMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        return breezeAuthService.doSendUserMobileCode(mobile, request, response);
    }

    @ApiOperation(value = "手机号验证码登录")
    @ApiOperationSupport(order = 40)
    @GetMapping("/login/mobile")
    public JsonContent<SaTokenInfo> doUserMobileLogin(@Valid @RequestBody UsernameMobileLoginArgs args) {
        return breezeAuthService.doUserMobileLogin(args);
    }

    @SaCheckLogin
    @ApiOperation(value = "当前登录人")
    @ApiOperationSupport(order = 50)
    @GetMapping("/user")
    public JsonContent<SysAccount> doGetCurrentUserInfo() {
        return breezeAuthService.doGetCurrentUserInfo();
    }

    @SaCheckLogin
    @ApiOperation(value = "当前菜单列表")
    @ApiOperationSupport(order = 60)
    @GetMapping("/navigation")
    public JsonContent<List<SysMenu>> doGetCurrentMenu() {
        return breezeAuthService.doGetCurrentMenu();
    }
    @SaCheckLogin
    @ApiOperation(value = "当前菜单树")
    @ApiOperationSupport(order = 65)
    @GetMapping("/navigation/tree")
    public JsonContent<List<Tree<String>>> doGetCurrentMenuTree() {
        return breezeAuthService.doGetCurrentMenuTree();
    }
    @SaCheckLogin
    @ApiOperation(value = "退出登录")
    @ApiOperationSupport(order = 90)
    @GetMapping("/logout")
    public JsonContent<Object> doLogout() {
        StpUtil.logout();
        return JsonContent.success();
    }
}
