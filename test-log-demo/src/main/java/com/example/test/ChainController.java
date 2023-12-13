package com.example.test;

import cn.fanzy.infra.ip.annotation.IpCheck;
import cn.fanzy.infra.web.chain.ChainHandler;
import cn.fanzy.infra.web.chain.Handler;
import cn.fanzy.infra.web.response.annotation.ResponseWrapper;
import cn.hutool.core.util.RandomUtil;
import com.example.test.chain.BizMoneyHandler;
import com.example.test.vo.DateVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 测试控制器
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chain")
public class ChainController {

    private final ChainHandler handler;
    @GetMapping("/1")
    public Serializable test1(String bizKey) {
        return handler.process("参数",bizKey);
    }
    @GetMapping("/2")
    public Serializable test2(String bizKey) {
        return handler.process("参数", BizMoneyHandler.class);
    }
}
