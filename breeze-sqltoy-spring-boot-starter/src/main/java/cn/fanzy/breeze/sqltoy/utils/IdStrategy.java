package cn.fanzy.breeze.sqltoy.utils;

public class IdStrategy {
    /**
     * 手工赋值
     */
    public static final String ASSIGN = "assign";
    /**
     * 数据库sequence
     */
    public static final String SEQUENCE = "sequence";
    /**
     * 数据库identity自增模式,oracle,db2中对应always identity
     */
    public static final String IDENTITY = "identity";
    /**
     * 自定义类产生一个不唯一的主键
     */
    public static final String GENERATOR = "generator";

    public static class Generator {

        public static final String DEFAULT = "default";
        /**
         * 32位
         */
        public static final String uuid = "uuid";
        public static final String redis = "redis";
        /**
         * 26位
         */
        public static final String nanoTime = "nanotime";
        /**
         * 16位雪花算法
         */
        public static final String snowflake = "snowflake";
    }

}
