package cn.fanzy.breeze.demo.controller;

import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.thread.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final RedissonClient redissonClient;

    @GetMapping("/lock")
    public JsonContent<Object> lock(){
        RLock rLock = redissonClient.getLock("lock");
        rLock.lock();
        log.info("执行方法。。。");
        ThreadUtil.sleep(3000);
        rLock.unlock();
        return JsonContent.success();
    }
}
