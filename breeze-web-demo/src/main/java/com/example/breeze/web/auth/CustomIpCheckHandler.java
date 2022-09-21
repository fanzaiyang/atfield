package com.example.breeze.web.auth;

import cn.fanzy.breeze.web.ip.service.BreezeIpCheckService;
import cn.fanzy.breeze.web.utils.SpringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomIpCheckHandler implements BreezeIpCheckService {
    @Override
    public void handler() {
        JdbcTemplate jdbcTemplate = SpringUtils.getBean(JdbcTemplate.class);
        // todo 查询数据库校验
    }
}
