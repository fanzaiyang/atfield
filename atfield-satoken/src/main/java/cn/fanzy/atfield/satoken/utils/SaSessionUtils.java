package cn.fanzy.atfield.satoken.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.atfield.core.utils.ObjectUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.List;


/**
 * sa 会话实用程序
 *
 * @author fanzaiyang
 * @date 2024/11/13
 */
public class SaSessionUtils {
    public static final String SA_LOGIN_NAME = SaSession.USER + "_NAME";

    /**
     * 设置
     *
     * @param key   钥匙
     * @param value 价值
     */
    public static void set(String key, Object value) {
        SaSession saSession = StpUtil.getSession();
        Assert.notNull(saSession, "Session is null");
        if (value instanceof String) {
            if (JSONUtil.isTypeJSON(value.toString())) {
                saSession.set(key, value);
                return;
            }
        }
        saSession.set(key, JSONUtil.toJsonStr(value));
    }

    /**
     * 获取模型
     *
     * @param key   钥匙
     * @param clazz 克拉兹
     * @return {@link T }
     */
    public static <T> T getModel(String key, Class<T> clazz) {
        SaSession saSession = StpUtil.getSession();
        Assert.notNull(saSession, "Session is null");
        return ObjectUtils.cast(saSession.get(key), clazz);
    }

    /**
     * 获取 STR
     *
     * @param key 钥匙
     * @return {@link String }
     */
    public static String getStr(String key) {
        SaSession saSession = StpUtil.getSession();
        if (saSession == null) {
            return null;
        }
        return saSession.getString(key);
    }

    /**
     * 获取列表
     *
     * @param key   钥匙
     * @param clazz 克拉兹
     * @return {@link List }<{@link T }>
     */
    public static <T> List<T> getList(String key, Class<T> clazz) {
        SaSession saSession = StpUtil.getSession();
        if (saSession == null) {
            return List.of();
        }
        String object = saSession.getString(key);
        if (StrUtil.isNotBlank(object)) {
            return JSONUtil.toList(object, clazz);
        }
        return List.of();
    }
}
