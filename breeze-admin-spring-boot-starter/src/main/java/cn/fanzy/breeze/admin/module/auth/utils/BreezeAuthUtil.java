package cn.fanzy.breeze.admin.module.auth.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.admin.module.auth.vo.CurrentUserInfoVo;
import cn.fanzy.breeze.web.utils.SpringUtils;

/**
 * 授权工具类
 */
public class BreezeAuthUtil {
    /**
     * 获取当前登录的账号
     *
     * @return String
     */
    public static String getCurrentAccountId() {
        return StpUtil.getLoginIdAsString();
    }
    public static CurrentUserInfoVo getCurrentAccount(){
        return SpringUtils.getBean(BreezeAdminAuthService.class).doGetCurrentUserInfo().getData();
    }
}
