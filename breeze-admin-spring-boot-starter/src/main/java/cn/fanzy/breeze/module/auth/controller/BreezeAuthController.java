package cn.fanzy.breeze.module.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.fanzy.breeze.module.auth.args.UsernamePasswordLoginArgs;
import cn.fanzy.breeze.module.auth.service.BreezeAuthService;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/${breeze.admin.prefix}/auth")
public class BreezeAuthController {

    private final BreezeAuthService breezeAuthService;

    @PostMapping("/login/pwd")
    public JsonContent<SaTokenInfo> doUserPwdLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserPwdLogin();
    }
    @PostMapping("/login/wx")
    public JsonContent<SaTokenInfo> doUserWxLogin(@Valid @RequestBody UsernamePasswordLoginArgs args) {
        return breezeAuthService.doUserWxLogin();
    }
}
