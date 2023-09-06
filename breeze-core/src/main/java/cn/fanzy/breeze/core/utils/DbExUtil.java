package cn.fanzy.breeze.core.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库额外工具类
 *
 * @author fanzaiyang
 * @date 2023/09/06
 */
public class DbExUtil {
    public static final String REGEX = "jdbc:(?<db>\\w+):.*((//)|@)(?<host>.+):(?<port>\\d+)(/|(;DatabaseName=)|:)(?<dbName>\\w+)\\??.*";

    public static String getSchema(String url) {
        return getDbInfo(url).getDbSchema();
    }

    public static String getDbType(String url) {
        return getDbInfo(url).getDbType();
    }

    public static String getDbHost(String url) {
        return getDbInfo(url).getDbHost();
    }

    /**
     * 获取数据库信息
     *
     * @param url url
     * @return {@link Matcher}
     */
    public static DbInfo getDbInfo(String url) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return DbInfo.builder()
                    .dbType(matcher.group("db"))
                    .dbHost(matcher.group("host"))
                    .dbSchema(matcher.group("dbName"))
                    .dbPort(matcher.group("port"))
                    .build();
        }
        throw new IllegalArgumentException("参数URL格式错误。");
    }

    /**
     * 数据库信息
     *
     * @author fanzaiyang
     * @date 2023/09/06
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DbInfo implements Serializable {

        private static final long serialVersionUID = 3162044106915123125L;

        /**
         * 数据库类型
         */
        private String dbType;

        /**
         * 数据库主机
         */
        private String dbHost;

        /**
         * db端口
         */
        private String dbPort;

        /**
         * 数据库架构
         */
        private String dbSchema;
    }
}
