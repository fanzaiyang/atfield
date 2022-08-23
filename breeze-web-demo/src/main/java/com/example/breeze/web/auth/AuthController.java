package com.example.breeze.web.auth;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {


    @SaCheckLogin
    @GetMapping("/user")
    public JsonContent<Object> getUser() {
        return JsonContent.success("登录认证：只有登录之后才能进入该方法");
    }
}
