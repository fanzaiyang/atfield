package cn.fanzy.breeze.admin.module.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.admin.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAuthService;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/${breeze.admin.prefix}/auth")
public class BreezeAuthController {

    private final BreezeAuthService breezeAuthService;

    @PostMapping("/login/pwd")
    public JsonContent<SaTokenInfo> doUserPwdLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserPwdLogin(args);
    }
    @PostMapping("/login/wx")
    public JsonContent<SaTokenInfo> doUserWxLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserWxLogin(args);
    }
    @GetMapping("/user")
    public JsonContent<SysAccount> doGetCurrentUserInfo() {
        return breezeAuthService.doGetCurrentUserInfo();
    }
}
