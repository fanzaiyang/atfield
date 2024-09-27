package cn.fanzy.atfield.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;

import java.util.List;

/**
 * date 实用程序
 *
 * @author fanzaiyang
 * @date 2024/09/18
 */
public class DateUtils extends DateUtil {

    /**
     * 获取开始日期
     *
     * @param valueList 值列表
     * @return {@link String }
     */
    public static String getBeginDate(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return DateUtil.parse(valueList.get(0)).toDateStr();
        }
        return null;
    }

    /**
     * 获取开始时间
     *
     * @param valueList 值列表
     * @return {@link String }
     */
    public static String getBeginTime(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return DateUtil.parse(
                    valueList.get(0).length() == 10 ? valueList.get(1) + " 00:00:00" : valueList.get(0)
            ).toString("yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }

    /**
     * 获取结束日期
     *
     * @param valueList 值列表
     * @return {@link String }
     */
    public static String getEndDate(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return DateUtil.parse(valueList.get(1)).toDateStr();
        }
        return null;
    }


    /**
     * 获取结束时间
     *
     * @param valueList 值列表
     * @return {@link String }
     */
    public static String getEndTime(List<String> valueList) {
        if (CollUtil.size(valueList) == 2) {
            return DateUtil.parse(
                    valueList.get(1).length() == 10 ? valueList.get(1) + " 00:00:00" : valueList.get(1)
            ).toString("yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }
}
