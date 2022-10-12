package com.example.breeze.web;

import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BreezeWebCacheTests {

    @Autowired
    private BreezeCacheService breezeCacheService;

    /**
     * 保存缓存内容
     */
    @Test
    void save() {
        // 过期秒数
        int expireSecond=300;
        breezeCacheService.save("key","value",300);
    }

    /**
     * 根据key获取缓存内容
     */
    @Test
    void get() {
        Object key = breezeCacheService.get("key");
        System.out.println(key);
    }

    /**
     * 根据Key删除缓存
     */
    @Test
    void delete() {
        breezeCacheService.remove("key");
    }
}
