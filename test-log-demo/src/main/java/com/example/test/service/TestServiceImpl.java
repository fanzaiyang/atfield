package com.example.test.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public String hello(String word) {
        return StrUtil.format("Service Test -> {}", word);
    }
}
