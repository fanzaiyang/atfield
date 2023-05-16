package cn.fanzy.breeze.web.logs.service;

import cn.fanzy.breeze.web.logs.model.AppInfoModel;
import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;
import cn.fanzy.breeze.web.logs.model.UserInfoModel;

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

    /**
     * 得到用户信息
     * 得到用户id
     *
     * @param userId 用户id
     * @return {@link UserInfoModel}
     */
    UserInfoModel getUserInfo(String userId);

    /**
     * 得到应用程序信息
     *
     * @param appId 应用程序id
     * @return {@link AppInfoModel}
     */
    AppInfoModel getAppInfo(String appId);
}
