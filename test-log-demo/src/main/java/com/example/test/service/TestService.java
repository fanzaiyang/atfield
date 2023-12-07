package com.example.test.service;

import cn.fanzy.infra.web.response.annotation.ResponseWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ts")
public interface TestService {

    /**
     * 你好
     *
     * @param word 单词
     * @return {@link String}
     */
    @ResponseWrapper
    @GetMapping("/hello")
    String hello(String word);

}
