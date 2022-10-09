package com.example.breeze.web.auth;

import cn.fanzy.breeze.web.ip.annotation.BreezeIpCheck;
import cn.fanzy.breeze.web.model.JsonContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {


    /**
     * 使用配置文件中配置的IP
     * @return
     */
    @BreezeIpCheck
    @GetMapping("/user")
    public JsonContent<Object> getUser() {
        return JsonContent.success("登录认证：只有登录之后才能进入该方法");
    }

    /**
     * 使用注解中配置的IP
     * @return
     */
    @BreezeIpCheck(handler = CustomIpCheckHandler.class)
    @GetMapping("/user/2")
    public JsonContent<Object> getUser2() {
        return JsonContent.success("登录认证：只有登录之后才能进入该方法");
    }
    @GetMapping("/user/3")
    public JsonContent<Object> getUser3(boolean flag) {
        SysUser user=null;
        if(flag){
            user=new SysUser();
        }
        user.getName();
        return JsonContent.success();
    }
}
