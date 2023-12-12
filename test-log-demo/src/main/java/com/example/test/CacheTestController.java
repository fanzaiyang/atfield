package com.example.test;

import cn.fanzy.infra.cache.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 缓存测试控制器
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

    /**
     * 收到
     *
     * @apiNote 获取缓存,获取缓存
     * @param key 钥匙
     * @return {@link Object}
     */
    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return cacheService.get(key);
    }

    /**
     * 收到
     *
     * @param key   钥匙
     * @param value 价值
     * @return {@link String}
     */
    @GetMapping("/put/{key}/{value}")
    public String get(@PathVariable String key, @PathVariable String value) {
        cacheService.put(key, value);
        return "ok";
    }

    /**
     * 去除
     *
     * @param key 钥匙
     * @return {@link String}
     */
    @GetMapping("/remove/{key}")
    public String remove(@PathVariable String key) {
        cacheService.remove(key);
        return "ok";
    }
}
