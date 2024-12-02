package cn.fanzy.atfield.satoken.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * STP 上下文
 *
 * @author fanzaiyang
 * @date 2024/12/02
 */
public class StpContext {
    /**
     * 登录 ID TL
     */
    private static final TransmittableThreadLocal<String> loginIdTl = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<String> loginNameTl = new TransmittableThreadLocal<>();

    public static void putLoginId(String loginId) {
        loginIdTl.set(loginId);
    }

    public static String getLoginId() {
        return loginIdTl.get();
    }

    public static void removeLoginId() {
        loginIdTl.remove();
    }

    public static void putLoginName(String loginName) {
        loginNameTl.set(loginName);
    }

    public static String getLoginName() {
        return loginNameTl.get();
    }

    public static void removeLoginName() {
        loginNameTl.remove();
    }
}
