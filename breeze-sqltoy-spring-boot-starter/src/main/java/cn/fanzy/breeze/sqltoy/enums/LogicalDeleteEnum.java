package cn.fanzy.breeze.sqltoy.enums;

/**
 * 逻辑删除枚举
 *
 * @author fanzaiyang
 * @since  2023-03-16
 */
public enum LogicalDeleteEnum {
    /**
     * 固定值
     */
    value,
    /**
     * uuid
     */
    uuid,
    /**
     * 雪花
     */
    snowflake,
    /**
     * nano时间
     */
    nanoTime;
}
