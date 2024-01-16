package cn.fanzy.atfield.satoken.login.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.satoken.login.property.LoginProperty;
import cn.hutool.core.text.StrPool;

/**
 * 登录存储实用程序
 *
 * @author fanzaiyang
 * @date 2024/01/16
 */
public class LoginStorageUtil {

    public static String getKey(String loginName, String clientIp, LoginProperty property) {
        if (property.isIsolationForIp()) {
            return property.getPrefix() + StrPool.COLON + clientIp + StrPool.COLON + loginName;
        }
        return property.getPrefix() + StrPool.COLON + loginName;
    }
}
