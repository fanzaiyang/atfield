package com.example.test;

import cn.fanzy.infra.web.response.annotation.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    @ResponseWrapper
    @GetMapping("/1")
    public String test1() {
        log.info("test1");
        return "test1";
    }
    @ResponseWrapper
    @GetMapping("/2")
    public String test2() {
       throw new RuntimeException("test2 exception");
    }
}
