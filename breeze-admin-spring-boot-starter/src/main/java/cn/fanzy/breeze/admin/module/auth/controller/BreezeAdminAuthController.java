package cn.fanzy.breeze.admin.module.auth.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.admin.module.auth.args.UsernameMobileLoginArgs;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.admin.module.auth.vo.ClientEnvVo;
import cn.fanzy.breeze.admin.module.auth.vo.CurrentUserInfoVo;
import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.web.code.annotation.BreezeCodeChecker;
import cn.fanzy.breeze.web.code.enums.BreezeCodeType;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import cn.hutool.core.lang.tree.Tree;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "「微风组件」授权登录")
@ApiSupport(author = "微风组件", order = 991001)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.auth?:/auth}}")
public class BreezeAdminAuthController {

    private final BreezeAdminAuthService breezeAuthService;

    @Operation(summary = "客户端环境", description = "获取客户端环境")
    @ApiOperationSupport(order = 1)
    @GetMapping("/env")
    public JsonContent<ClientEnvVo> getClientEnv(HttpServletRequest request) {
        return breezeAuthService.getClientEnv(request);
    }

    @Operation(summary = "是否登录", description = "是否登录")
    @ApiOperationSupport(order = 2)
    @GetMapping("/is/login")
    public JsonContent<Boolean> isLogin() {
        return JsonContent.success(StpUtil.isLogin());
    }

    @Operation(summary = "登录前", description = "登录前调用此接口，用于判断是否需要显示验证码。true显示，false不显示")
    @ApiOperationSupport(order = 10)
    @Parameter(name = "username", description = "登录名", required = true)
    @GetMapping("/login/before")
    public JsonContent<Boolean> doUserPwdLoginBefore(String username) {
        return breezeAuthService.doUserPwdLoginBefore(username);
    }

    @Operation(summary = "发送图片验证码",description = "需要传入clientId")
    @ApiOperationSupport(order = 15)
    @GetMapping("/login/image/code/send")
    public JsonContent<String> doSendUserImageCode(HttpServletRequest request, HttpServletResponse response) {
        return breezeAuthService.doSendUserImageCode(request, response);
    }

    @BreezeSafe
    @Operation(summary = "用户名密码登录")
    @ApiOperationSupport(order = 20)
    @PostMapping("/login/pwd")
    public JsonContent<SaTokenInfo> doUserPwdLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserPwdLogin(args);
    }


    @Operation(summary = "发送手机验证码")
    @ApiOperationSupport(order = 35)
    @Parameter(name = "mobile", description = "手机号", required = true)
    @GetMapping("/login/mobile/code/send")
    public JsonContent<Object> doSendUserMobileCode(String mobile, HttpServletRequest request, HttpServletResponse response) {
        return breezeAuthService.doSendUserMobileCode(mobile, request, response);
    }

    @BreezeCodeChecker(BreezeCodeType.SMS)
    @Operation(summary = "手机号验证码登录")
    @ApiOperationSupport(order = 40)
    @GetMapping("/login/mobile")
    public JsonContent<SaTokenInfo> doUserMobileLogin(@Valid @RequestBody UsernameMobileLoginArgs args) {
        return breezeAuthService.doUserMobileLogin(args);
    }

    @SaCheckLogin
    @Operation(summary = "当前登录人")
    @ApiOperationSupport(order = 50)
    @GetMapping("/user")
    public JsonContent<CurrentUserInfoVo> doGetCurrentUserInfo() {
        return breezeAuthService.doGetCurrentUserInfo();
    }

    @SaCheckLogin
    @Operation(summary = "当前菜单列表")
    @ApiOperationSupport(order = 60)
    @GetMapping("/navigation")
    public JsonContent<List<SysMenu>> doGetCurrentMenu() {
        return breezeAuthService.doGetCurrentMenu();
    }

    @SaCheckLogin
    @Operation(summary = "当前菜单树")
    @ApiOperationSupport(order = 65)
    @GetMapping("/navigation/tree")
    public JsonContent<List<Tree<String>>> doGetCurrentMenuTree() {
        return breezeAuthService.doGetCurrentMenuTree();
    }

    @SaCheckLogin
    @Operation(summary = "退出登录")
    @ApiOperationSupport(order = 90)
    @GetMapping("/logout")
    public JsonContent<Object> doLogout() {
        StpUtil.logout();
        return JsonContent.success();
    }
}
