package com.example.test;

import cn.fanzy.infra.captcha.CaptchaService;
import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.web.json.model.R;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/code")
public class CaptchaController {
    private final CaptchaService captchaService;
    @GetMapping("1")
    public void code1(){
        CaptchaCode client = captchaService.createAndSend(CaptchaType.IMAGE, "client");
        log.info(JSONUtil.toJsonStr(client));
    }
    @GetMapping("2")
    public R<Boolean> code2(String code){
        captchaService.verify(CaptchaType.IMAGE, "client", code);
        return R.ok();
    }
}