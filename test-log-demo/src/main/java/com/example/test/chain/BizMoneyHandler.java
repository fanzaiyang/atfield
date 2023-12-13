package com.example.test.chain;

import cn.fanzy.infra.web.chain.Handler;

/**
 * 商业资金管理员
 *
 * @author fanzaiyang
 * @date 2023/12/13
 */
public interface BizMoneyHandler extends Handler {
    @Override
    default String getGroupName() {
        return "bizMoneyHandler";
    }
}
