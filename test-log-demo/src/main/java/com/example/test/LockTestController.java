package com.example.test;

import cn.fanzy.infra.redis.annotation.Lock;
import cn.fanzy.infra.redis.annotation.LockForm;
import cn.fanzy.infra.redis.annotation.LockRate;
import cn.fanzy.infra.web.json.model.R;
import cn.hutool.core.thread.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 测试控制器
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/lock")
public class LockTestController {

    @Lock("test-lock")
    @GetMapping("/dist")
    public R<String> lock(String keyword) {
        ThreadUtil.sleep(10, TimeUnit.SECONDS);
        return R.ok(keyword);
    }

    @LockForm("test-lock-form")
    @GetMapping("/form")
    public R<String> form(String param) {
        ThreadUtil.sleep(10, TimeUnit.SECONDS);
        return R.ok(param);
    }

    @LockRate
    @GetMapping("/rate")
    public R<String> rate(String param) {
        ThreadUtil.sleep(10, TimeUnit.SECONDS);
        return R.ok(param);
    }
}