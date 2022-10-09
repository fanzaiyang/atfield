package com.example.breeze.web.auth;

import cn.fanzy.breeze.web.ip.service.BreezeIpCheckService;
import cn.fanzy.breeze.web.utils.SpringUtils;

public class CustomIpCheckHandler implements BreezeIpCheckService {
    @Override
    public void handler() {
        // todo 查询数据库校验
    }
}
