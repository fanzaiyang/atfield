package com.example.breeze.web;

import cn.fanzy.breeze.wechat.cp.WxCpConfiguration;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class BreezeWxCpTests {



    @Test
    void getAccessToken() throws WxErrorException {
        WxCpService wxCpService = WxCpConfiguration.getCpService();
        assert wxCpService != null;
        String accessToken = wxCpService.getAccessToken();
    }
}
