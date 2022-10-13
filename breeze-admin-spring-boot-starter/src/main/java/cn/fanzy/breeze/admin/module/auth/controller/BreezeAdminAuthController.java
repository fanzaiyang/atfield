package cn.fanzy.breeze.admin.module.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "「微风组件」授权登录")
@ApiSupport(author = "微风组件", order = 991001)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix}")
public class BreezeAdminAuthController {

    private final BreezeAdminAuthService breezeAuthService;

    @ApiOperation(value = "登录前", notes = "登录前调用此接口，用于判断是否需要显示验证码。")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParam(name = "username", value = "登录名")
    @GetMapping("/login/before")
    public JsonContent<Object> doUserPwdLoginBefore(String username) {
        return breezeAuthService.doUserPwdLoginBefore(username);
    }

    @BreezeSafe
    @ApiOperation(value = "用户名密码登录")
    @ApiOperationSupport(order = 2)
    @PostMapping("/login/pwd")
    public JsonContent<SaTokenInfo> doUserPwdLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserPwdLogin(args);
    }

    @ApiOperation(value = "微信扫码登录")
    @ApiOperationSupport(order = 3)
    @PostMapping("/login/wx")
    public JsonContent<SaTokenInfo> doUserWxLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserWxLogin(args);
    }

    @ApiOperation(value = "当前登录人")
    @ApiOperationSupport(order = 4)
    @GetMapping("/user")
    public JsonContent<SysAccount> doGetCurrentUserInfo() {
        return breezeAuthService.doGetCurrentUserInfo();
    }
}
