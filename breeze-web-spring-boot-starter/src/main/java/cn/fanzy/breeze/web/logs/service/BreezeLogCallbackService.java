package cn.fanzy.breeze.web.logs.service;

import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;

/**
 * 微风日志回调服务
 *
 * @author fanzaiyang
 * @since 2022-10-11
 */
public interface BreezeLogCallbackService {

    /**
     * 回调
     *
     * @param args arg游戏
     */
    void callback(BreezeRequestArgs args);
}
