package cn.fanzy.atfield.sqltoy.delflag.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 逻辑删除标志上下文
 * 临时修改逻辑删除标志的上下文，用于在多线程环境下传递逻辑删除标志信息
 *
 * @author fanzaiyang
 * @date 2024/12/02
 */
public class DelFlagContext {
    /**
     * 删除字段 TL
     */
    private static final TransmittableThreadLocal<String> deleteFieldTl = new TransmittableThreadLocal<>();
    /**
     * 未删除的值
     */
    private static final TransmittableThreadLocal<String> noDeleteValueTl = new TransmittableThreadLocal<>();
    /**
     * 删除的值
     */
    private static final TransmittableThreadLocal<String> deleteValueTl = new TransmittableThreadLocal<>();

    /**
     * Ignore 值 TL
     */
    private static final TransmittableThreadLocal<Boolean> ignoreValueTl = new TransmittableThreadLocal<>();

    /**
     * 放置 Delete 字段
     *
     * @param deleteField 删除字段
     */
    public static void putDeleteField(String deleteField) {
        deleteFieldTl.set(deleteField);
    }

    /**
     * 获取删除字段
     *
     * @return {@link String }
     */
    public static String getDeleteField() {
        return deleteFieldTl.get();
    }

    /**
     * 删除 删除字段
     */
    public static void removeDeleteField() {
        deleteFieldTl.remove();
    }

    /**
     * 不放 delete 值
     *
     * @param noDeleteValue 无 Delete 值
     */
    public static void putNoDeleteValue(String noDeleteValue) {
        noDeleteValueTl.set(noDeleteValue);
    }

    /**
     * 获取无 Delete 值
     *
     * @return {@link String }
     */
    public static String getNoDeleteValue() {
        return noDeleteValueTl.get();
    }

    /**
     * 删除 No Delete 值
     */
    public static void removeNoDeleteValue() {
        noDeleteValueTl.remove();
    }

    /**
     * put delete 值
     *
     * @param deleteValue 删除值
     */
    public static void putDeleteValue(String deleteValue) {
        deleteValueTl.set(deleteValue);
    }

    /**
     * 获取 Delete 值
     *
     * @return {@link String }
     */
    public static String getDeleteValue() {
        return deleteValueTl.get();
    }

    /**
     * 删除 删除值
     */
    public static void removeDeleteValue() {
        deleteValueTl.remove();
    }

    /**
     * Put Ignore 值
     *
     * @param ignoreValue ignore 值
     */
    public static void putIgnore(boolean ignoreValue) {
        ignoreValueTl.set(ignoreValue);
    }

    /**
     * 获取忽略值
     *
     * @return {@link Boolean }
     */
    public static Boolean getIgnore() {
        return ignoreValueTl.get();
    }

    /**
     * 删除忽略值
     */
    public static void removeIgnore() {
        ignoreValueTl.remove();
    }
}
