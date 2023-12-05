package com.example.test;

import cn.fanzy.infra.cache.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/test/cache")
public class CacheTestController {
    private final CacheService cacheService;

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return cacheService.get(key);
    }

    @GetMapping("/put/{key}/{value}")
    public String get(@PathVariable String key, @PathVariable String value) {
        cacheService.put(key, value);
        return "ok";
    }
}
