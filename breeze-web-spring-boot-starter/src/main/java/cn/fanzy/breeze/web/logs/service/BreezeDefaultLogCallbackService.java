package cn.fanzy.breeze.web.logs.service;

import cn.fanzy.breeze.web.logs.model.AppInfoModel;
import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;
import cn.fanzy.breeze.web.logs.model.UserInfoModel;

/**
 * 微风默认日志回调服务
 *
 * @author fanzaiyang
 * @date 2023-04-23
 */
public class BreezeDefaultLogCallbackService implements BreezeLogCallbackService{
    @Override
    public void callback(BreezeRequestArgs args) {

    }

    @Override
    public UserInfoModel getUserInfo() {
        return new UserInfoModel();
    }

    @Override
    public AppInfoModel getAppInfo() {
        return new AppInfoModel();
    }
}
