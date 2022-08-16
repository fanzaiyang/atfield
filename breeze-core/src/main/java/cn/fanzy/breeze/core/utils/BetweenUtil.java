package cn.fanzy.breeze.core.utils;



import cn.fanzy.breeze.core.datetime.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * <p>
 * 比较工具工具类
 * </p>
 * 用于比较一个给定的值是否在一个指定的范围内，其主要作用如下：
 * <ol>
 * <li>判断给定的数字是否在指定的数字范围之内</li>
 * <li>判断给定的时间是否在指定的时间范围内</li>
 * </ol>
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
public class BetweenUtil {

    /**
     * <p>
     * 判断给定的值是否在边界范围内，如不在边界范围内则返回边界值，若在范围内则返回当前值
     * </p>
     *
     * <p>
     * 例如 compareValue=1，startValue=2 ，endValue=8时的返回值为2
     * </p>
     * <p>
     * 例如 compareValue=9，startValue=2 ，endValue=8时的返回值为8
     * </p>
     * <p>
     * 例如 compareValue=4，startValue=2 ，endValue=8时的返回值为4
     * </p>
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 判断给定的值是否在边界范围内，如不在边界范围内则返回边界值，若在范围内则返回当前值
     */
    public static Integer get(Integer compareValue, Integer startValue, Integer endValue) {
        if (null == compareValue) {
            compareValue = 0;
        }
        if (null == startValue) {
            startValue = 0;
        }
        if (null == endValue) {
            endValue = 0;
        }
        if (compareValue < startValue) {
            return startValue;
        }
        if (compareValue > endValue) {
            return endValue;
        }
        return compareValue;
    }

    /**
     * <p>
     * 判断给定的值是否在边界范围内，如不在边界范围内则返回边界值，若在范围内则返回当前值
     * </p>
     *
     * <p>
     * 例如 compareValue=1，startValue=2 ，endValue=8时的返回值为2
     * </p>
     * <p>
     * 例如 compareValue=9，startValue=2 ，endValue=8时的返回值为8
     * </p>
     * <p>
     * 例如 compareValue=4，startValue=2 ，endValue=8时的返回值为4
     * </p>
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 判断给定的值是否在边界范围内，如不在边界范围内则返回边界值，若在范围内则返回当前值
     */
    public static Long get(Long compareValue, Long startValue, Long endValue) {
        if (null == compareValue) {
            compareValue = 0L;
        }
        if (null == startValue) {
            startValue = 0L;
        }
        if (null == endValue) {
            endValue = 0L;
        }
        if (compareValue < startValue) {
            return startValue;
        }
        if (compareValue > endValue) {
            return endValue;
        }
        return compareValue;
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为true
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isBetween(Integer compareValue, Integer startValue, Integer endValue) {
        if (null == compareValue) {
            compareValue = 0;
        }
        if (null == startValue) {
            startValue = 0;
        }
        if (null == endValue) {
            endValue = 0;
        }
        return compareValue >= startValue && compareValue <= endValue;
    }

    /**
     * <p>
     * 判断给定的值是否不在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     * <p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为false
     * </p>
     * <p>
     * 例如 compareValue=1，startValue=2 ，endValue=8时的返回值为true
     * </p>
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否不在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isNotBetween(Integer compareValue, Integer startValue, Integer endValue) {
        return !isBetween(compareValue, startValue, endValue);
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     * <p>
     * 注意：包含边界 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为true
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isBetween(Long compareValue, Long startValue, Long endValue) {
        if (null == compareValue) {
            compareValue = 0L;
        }
        if (null == startValue) {
            startValue = 0L;
        }
        if (null == endValue) {
            endValue = 0L;
        }
        return compareValue >= startValue && compareValue <= endValue;
    }

    /**
     * <p>
     * 判断给定的值是否不在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     * <p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为false
     * </p>
     * <p>
     * 例如 compareValue=1，startValue=2 ，endValue=8时的返回值为true
     * </p>
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否不在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isNotBetween(Long compareValue, Long startValue, Long endValue) {
        return !isBetween(compareValue, startValue, endValue);
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为true
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isBetween(Float compareValue, Float startValue, Float endValue) {
        if (null == compareValue) {
            compareValue = 0F;
        }
        if (null == startValue) {
            startValue = 0F;
        }
        if (null == endValue) {
            endValue = 0F;
        }
        return (compareValue - startValue >= 0) && (compareValue - endValue <= 0);
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     *
     * <p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为false
     * </p>
     * <p>
     * 例如 compareValue=1，startValue=2 ，endValue=8时的返回值为true
     * </p>
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否不在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isNotBetween(Float compareValue, Float startValue, Float endValue) {
        return !isBetween(compareValue, startValue, endValue);
    }

    /**
     * <p>
     * 判断给定的值是否不在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为true
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isBetween(Double compareValue, Double startValue, Double endValue) {
        if (null == compareValue) {
            compareValue = 0D;
        }
        if (null == startValue) {
            startValue = 0D;
        }
        if (null == endValue) {
            endValue = 0D;
        }
        return (compareValue - startValue >= 0) && (compareValue - endValue <= 0);
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：包含边界
     * </p>
     *
     * <p>
     * 例如 compareValue=2，startValue=2 ，endValue=8时的返回值为false
     * </p>
     * <p>
     * 例如 compareValue=1，startValue=2 ，endValue=8时的返回值为true
     * </p>
     *
     * @param compareValue 给定的值，如果给定的值为null，则默认替换为0
     * @param startValue   比较范围的开始值，如果给定的值为null，则默认替换为0
     * @param endValue     比较范围的结束值，如果给定的值为null，则默认替换为0
     * @return 给定的值是否不在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isNotBetween(Double compareValue, Double startValue, Double endValue) {
        return !isBetween(compareValue, startValue, endValue);
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：
     * </p>
     * <p>
     * 1. 不包含边界
     * </p>
     * <p>
     * 2. 任意一个值为null则返回值为false
     * </p>
     *
     * <p>
     * 例如 compareValue=2020-12-12 12:12:12，startValue=2020-12-12 12:12:12
     * ，endValue=2021-12-12 12:12:12时的返回值为true
     * </p>
     * <p>
     * 例如 compareValue=2020-12-11 12:12:12，startValue=2020-12-12 12:12:12
     * ，endValue=2021-12-12 12:12:12时的返回值为false
     * </p>
     *
     * @param compareValue 给定的值
     * @param startValue   比较范围的开始值
     * @param endValue     比较范围的结束值
     * @return 给定的值是否在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isBetween(Date compareValue, Date startValue, Date endValue) {
        if (null == compareValue || null == startValue || null == endValue) {
            return false;
        }

        return isBetween(DateTimeUtil.date2LocalDateTime(compareValue), DateTimeUtil.date2LocalDateTime(startValue),
                DateTimeUtil.date2LocalDateTime(endValue));
    }

    /**
     * <p>
     * 判断给定的值是否不在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 1. 包含边界
     * </p>
     * 2. 任意一个值为null则返回值为true
     *
     * @param compareValue 给定的值
     * @param startValue   比较范围的开始值
     * @param endValue     比较范围的结束值
     * @return 给定的值是否不在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isNotBetween(Date compareValue, Date startValue, Date endValue) {
        return !isBetween(compareValue, startValue, endValue);
    }

    /**
     * <p>
     * 判断给定的值是否在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 注意：
     * </p>
     * <p>
     * 1. 包含边界
     * </p>
     * 2. 任意一个值为null则返回值为false
     *
     * <p>
     * 例如 compareValue=2020-12-12 12:12:12，startValue=2020-12-12 12:12:12
     * ，endValue=2021-12-12 12:12:12时的返回值为true
     * </p>
     * <p>
     * 例如 compareValue=2020-12-11 12:12:12，startValue=2020-12-12 12:12:12
     * ，endValue=2021-12-12 12:12:12时的返回值为false
     * </p>
     *
     * @param compareValue 给定的值
     * @param startValue   比较范围的开始值
     * @param endValue     比较范围的结束值
     * @return 给定的值是否在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isBetween(LocalDateTime compareValue, LocalDateTime startValue, LocalDateTime endValue) {
        if (null == compareValue || null == startValue || null == endValue) {
            return false;
        }
        return (!compareValue.isBefore(startValue)) && (!compareValue.isAfter(endValue));
    }

    /**
     * <p>
     * 判断给定的值是否不在指定的数据范围的区间内
     * </p>
     *
     * <p>
     * 1. 包含边界
     * </p>
     * 2. 任意一个值为null则返回值为false
     *
     * @param compareValue 给定的值
     * @param startValue   比较范围的开始值
     * @param endValue     比较范围的结束值
     * @return 给定的值是否不在指定的数据范围的区间内, 如果在则返回为true, 否则为false
     */
    public static boolean isNotBetween(LocalDateTime compareValue, LocalDateTime startValue, LocalDateTime endValue) {
        if (null == compareValue || null == startValue || null == endValue) {
            return false;
        }
        return !isBetween(compareValue, startValue, endValue);
    }

}
