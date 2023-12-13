package com.example.test.chain;

import cn.fanzy.infra.web.chain.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * biz1处理程序
 *
 * @author fanzaiyang
 * @date 2023/12/13
 */
@Slf4j
@Service
public class Biz3Handler implements Handler {
    @Override
    public Serializable process(Serializable request) {
        log.info("处理业务3。。");
        return request;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
