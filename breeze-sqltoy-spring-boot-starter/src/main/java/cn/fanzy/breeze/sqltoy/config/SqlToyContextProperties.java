package cn.fanzy.breeze.sqltoy.config;

import java.io.Serializable;
import java.util.Map;

import cn.fanzy.breeze.sqltoy.core.enums.CacheType;
import cn.hutool.core.lang.Snowflake;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhongxuchen
 * @version v1.0, Date:2020年2月20日
 */
@Data
@ConfigurationProperties(prefix = "spring.sqltoy")
public class SqlToyContextProperties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8313800149129731930L;

    /**
     * 指定sql.xml 文件路径,多个路径用逗号分隔。默认值classpath:sql。在resource/sql
     */
    private String sqlResourcesDir = "classpath:sql";

    /**
     * 缓存翻译的配置文件
     */
    private String translateConfig;

    /**
     * 针对不同数据库函数进行转换,非必须属性,close 表示关闭
     */
    private Object functionConverts;

    /**
     * 数据库方言，一般无需设置
     */
    private String dialect;

    /**
     * Sqltoy实体Entity包路径,非必须属性
     */
    private String[] packagesToScan;

    /**
     * 额外注解class类，已经没有必要
     */
    private String[] annotatedClasses;

    /**
     * 具体的sql.xml 文件资源
     */
    private String[] sqlResources;

    /**
     * 需要重复执行查询的数据库
     */
    private String[] redoDataSources;

    /**
     * 是否开启debug模式(默认为false)
     */
    private boolean debug=false;

    /**
     * 批量操作，每批次数量,默认200
     */
    private Integer batchSize=200;

    /**
     * 默认查询数据库端提取记录量,一般无需设置
     */
    private int fetchSize = -1;

    /**
     * 分页最大单页数据量(默认是5万)
     */
    private Integer pageFetchSizeLimit=50000;

    /**
     * 超时打印sql(毫秒,默认30秒)
     */
    private Integer printSqlTimeoutMillis=30000;

    /**
     * sql文件脚本变更检测间隔时长(秒)
     */
    private Integer scriptCheckIntervalSeconds;

    /**
     * 缓存更新、sql脚本更新 延迟多少秒开始检测
     */
    private Integer delayCheckSeconds=30;

    private String encoding="UTF-8";

    /**
     * 统一字段处理器,实现IUnifyFieldsHandler接口
     */
    @Deprecated
    private String unifyFieldsHandler;

    /**
     * 数据库方言参数配置
     */
    private Map<String, String> dialectConfig;

    /**
     * sqltoy默认数据库
     */
    private String defaultDataSource;

    /**
     * 数据库保留字,用逗号分隔
     */
    private String[] reservedWords;

    /**
     * 缓存管理器
     */
    private String translateCacheManager;

    /**
     * 字段类型转换器
     */
    private String typeHandler;

    /**
     * 自定义数据源选择器
     */
    private String dataSourceSelector;

    /**
     * 缓存类型，默认ehcache，可选caffeine
     */
    private CacheType cacheType = CacheType.ehcache;

    /**
     * 当发现有重复sqlId时是否抛出异常，终止程序执行
     */
    private boolean breakWhenSqlRepeat = true;

    /**
     * 连接管理的实现扩展定义
     */
    private String connectionFactory;

    /**
     * 安全私钥
     */
    private String securePrivateKey;

    /**
     * 安全公钥
     */
    private String securePublicKey;

    /**
     * 字段安全加密处理器定义(开发者可以自行扩展，sqltoy默认提供了RSA的实现)
     */
    private String fieldsSecureProvider;

    /**
     * 字段展示安全脱敏处理器(sqltoy默认提供了实现，此处提供不满足的情况下的自行扩展)
     */
    private String desensitizeProvider;

    /**
     * add 2022-4-26 自定义filter处理器(预留备用)
     */
    private String customFilterHandler;

    /**
     * sql执行超时处理器
     */
    private String overTimeSqlHandler;

    /**
     * 获取MetaData的列标题处理策略：default:不做处理;upper:转大写;lower
     */
    private String columnLabelUpperOrLower = "default";

    /**
     * snowflake参数,如不设置框架自动以本机IP来获取
     */
    private Snowflake snowflake=new Snowflake();


    @Data
    public static class Snowflake{
        /**
         * snowflake 集群节点id<31
         */
        private Integer workerId;

        /**
         * 数据中心id<31
         */
        private Integer dataCenterId;
        /**
         * 服务器id(3位数字)，用于22位和26位主键生成，不设置会自动根据本机IP生成
         */
        private Integer serverId;
    }




    public Integer getScriptCheckIntervalSeconds() {
        if(scriptCheckIntervalSeconds!=null){
            return scriptCheckIntervalSeconds;
        }
        return debug?3:15;
    }
}
