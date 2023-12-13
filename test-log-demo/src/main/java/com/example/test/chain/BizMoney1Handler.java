package com.example.test.chain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 业务资金1管理员
 *
 * @author fanzaiyang
 * @date 2023/12/13
 */
@Slf4j
@Service
public class BizMoney1Handler implements BizMoneyHandler{
    @Override
    public Serializable process(Serializable request) {
        log.info("BizMoney1Handler 1 ...");
        return request;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
