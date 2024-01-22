package cn.fanzy.atfield.sqltoy.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;

/**
 * 删除标识工具类
 *
 * @author fanzaiyang
 * @date 2024-01-21
 */
public class LogicDeleteUtil {

    /**
     * 得到配置
     *
     * @return {@link SqltoyExtraProperties}
     */
    public static SqltoyExtraProperties getProperties() {
        return SpringUtils.getBean(SqltoyExtraProperties.class);
    }

    /**
     * 得到逻辑删除的值
     *
     * @return {@link String}
     */
    public static String getLogicDeleteValue() {
        return getProperties().getLogicDeleteValue();
    }

    /**
     * 得到未删除的值
     *
     * @return {@link String}
     */
    public static String getLogicNotDeleteValue() {
        return getProperties().getLogicNotDeleteValue();
    }

    /**
     * 得到逻辑删除田
     *
     * @return {@link String}
     */
    public static String getLogicDeleteField() {
        return getProperties().getLogicDeleteField();
    }
}
