/**
 *
 */
package cn.fanzy.breeze.core.datetime;

import cn.fanzy.breeze.core.exception.CustomException;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * <p>
 * 时间转换解析工具
 * </p>
 * 该工具的主要作用是将LocalDateTime和Date形式的时间互相转换以及将字符串格式的时间和日期时间互相转换。该工具是一个线程安全类的工具，其主要的功能如下：
 * <ol>
 * <li>将LocalDateTime和Date形式的时间互相转换</li>
 * <li>将指定格式的字符串按解析为日期时间</li>
 * <li>将日期时间按照指定的格式转换成字符串</li>
 * </ol>
 *
 * @author fanzaiyang
 * @version 2021/09/07
 */
@Slf4j
public final class DateTimeUtil {

    /**
     * 默认的时区,上海
     */
    private final static String DEFAULT_ZONE = "Asia/Shanghai";

    /**
     * 默认的日期时间的字符串形式 yyyy-MM-dd HH:mm:ss形式
     */
    private final static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认的日期时间的字符串形式 yyyy-MM-dd HH:mm
     */
    private final static String SIMPLE_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 默认的日期字符串形式 yyyy-MM-dd
     */
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 获取北京时间的ZoneId
     *
     * @return 北京时间的ZoneId
     */
    public static synchronized ZoneId zoneIdOfChina() {
        return ZoneId.of(DEFAULT_ZONE);
    }

    /**
     * 将Date转换为 LocalDateTime
     *
     * @param date 给定的时间
     * @return 转换后的时间
     */
    public static synchronized LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, zoneIdOfChina());
    }

    /**
     * 将LocalDateTime转换为 Date
     *
     * @param localDateTime 给定的时间
     * @return 转换后的时间
     */
    public static synchronized Date localDateTime2Date(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        Instant instant = localDateTime.atZone(zoneIdOfChina()).toInstant();
        return Date.from(instant);
    }

    /**
     * 返回自1970年1月1日以来，由此LocalDateTime对应的 Date对象表示的00:00:00 GMT的毫秒 数 。
     *
     * @param localDateTime 给定的时间
     * @return 由此LocalDateTime对应的 Date对象表示的00:00:00 GMT的毫秒 数 。
     */
    public static synchronized Long getTime(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return localDateTime2Date(localDateTime).getTime();
    }

    /**
     * 返回自1970年1月1日以来，由此 Date对象表示的00:00:00 GMT的毫秒 数 。
     *
     * @param date 给定的时间
     * @return 由此 Date对象表示的00:00:00 GMT的毫秒 数 。
     */
    public static synchronized Long getTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.getTime();
    }

    /**
     * 使用从1970-01-01T00：00：00Z的时代开始的毫秒 数获得一个LocalDateTime的实例。
     *
     * @param milliseconds 从1970-01-01T00：00：00Z的时代开始的毫秒 数
     * @return 从1970-01-01T00：00：00Z的时代开始的毫秒 数获得一个LocalDateTime的实例
     */
    public static synchronized LocalDateTime getLocalDateTime(long milliseconds) {
        return date2LocalDateTime(new Date(milliseconds));
    }

    /**
     * <p>
     * 将字符串解析为LocalDateTime 形式的时间
     * </p>
     *
     * 默认采用yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd HH:mm 或 yyyy-MM-dd 形式解析
     *
     * @param timeStr 需要解析的字符串
     *
     * @return LocalDateTime形式的时间
     * @throws CustomException 解析时出现了问题
     */
    public static synchronized LocalDateTime parse(String timeStr) throws CustomException {
        return parse(timeStr, DEFAULT_DATETIME_FORMAT, DEFAULT_DATE_FORMAT, SIMPLE_DATETIME_FORMAT);
    }

    /**
     * 将字符串解析为LocalDateTime 形式的时间
     *
     * @param timeStr  需要解析的字符串
     * @param patterns 解析规则 当未填写解析规则时
     * @return LocalDateTime形式的时间
     * @throws CustomException 解析时出现了问题
     */
    public static synchronized LocalDateTime parse(String timeStr, String... patterns) throws CustomException {

        return date2LocalDateTime(DateUtil.parse(timeStr, patterns));
    }

    /**
     * 将LocalDateTime形式的时间格式化为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param localDateTime 给定的时间
     * @return yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static synchronized String format(LocalDateTime localDateTime) {
        return format(localDateTime, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将LocalDateTime形式的时间格式化为 格式化为指定形式的字符串
     *
     * @param localDateTime 给定的时间
     * @param pattern       格式化形式，例如yyyy-MM-dd HH:mm:ss
     * @return 指定形式的字符串
     */
    public static synchronized String format(LocalDateTime localDateTime, String pattern) {
        if (null == localDateTime) {
            return null;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return format.format(localDateTime);
    }

    /**
     * 将Date形式的时间格式化为 格式化为指定形式的字符串
     *
     * @param date    给定的时间
     * @param pattern 格式化形式，例如yyyy-MM-dd HH:mm:ss
     * @return 指定形式的字符串
     */
    public static synchronized String format(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return format.format(date2LocalDateTime(date));
    }

    /**
     * 将Date形式的时间格式化为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date 给定的时间
     * @return yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static synchronized String format(Date date) {
        return format(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将Date形式的时间格式化为yyyy-MM-dd格式的字符串
     *
     * @param date 给定的时间
     * @return yyyy-MM-dd 格式的字符串
     */
    public static synchronized String formatDate(Date date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将Date形式的时间格式化为yyyy-MM-dd格式的字符串
     *
     * @param localDateTime 给定的时间
     * @return yyyy-MM-dd 格式的字符串
     */
    public static synchronized String formatDate(LocalDateTime localDateTime) {
        return format(localDateTime, DEFAULT_DATE_FORMAT);
    }

}
