package com.example.breeze.web.auth;

import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.safe.annotation.BreezeSafe;
import cn.fanzy.breeze.web.safe.utils.BreezeSafeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth/safe")
public class SafeController {


    /**
     * 使用配置文件中配置的IP
     *
     * @return
     */
    @BreezeSafe
    @GetMapping("/check")
    public JsonContent<Object> getUser(String username) {
        Assert.isTrue(StrUtil.equals(username, "admin"), "该用户不存在！" + BreezeSafeUtil.getErrorMsg(username));
        return JsonContent.success("登录认证：只有登录之后才能进入该方法");
    }
}
