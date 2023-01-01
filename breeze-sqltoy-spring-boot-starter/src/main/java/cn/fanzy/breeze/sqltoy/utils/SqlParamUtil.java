package cn.fanzy.breeze.sqltoy.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;

/**
 * @author fanzaiyang
 */
public class SqlParamUtil {
    /**
     * 将in的参数，构建成'a','b','c'
     *
     * @param args Collection
     * @return String
     */
    public static String buildInArgs(Collection args) {
        return StrUtil.format("'{}'", String.join("','", args));
    }
}
