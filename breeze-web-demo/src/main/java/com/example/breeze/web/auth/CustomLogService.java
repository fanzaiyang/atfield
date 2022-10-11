package com.example.breeze.web.auth;

import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CustomLogService implements BreezeLogCallbackService {
    @Override
    public void callback(BreezeRequestArgs args) {
        log.info(JSONUtil.toJsonStr(args));
    }
}
