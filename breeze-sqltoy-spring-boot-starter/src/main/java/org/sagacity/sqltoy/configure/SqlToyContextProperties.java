package org.sagacity.sqltoy.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhongxuchen
 * @version v1.0,Date:2020年2月20日
 */
@Data
@ConfigurationProperties(prefix = "spring.sqltoy")
public class SqlToyContextProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313800149129731930L;

	/**
	 * 指定sql.xml 文件路径,多个路径用逗号分隔
	 */
	private String sqlResourcesDir;

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
	 * es的配置
	 */
	private Elastic elastic;

	/**
	 * 是否开启debug模式(默认为false)
	 */
	private Boolean debug;

	/**
	 * 批量操作，每批次数量,默认200
	 */
	private Integer batchSize;

	/**
	 * 默认查询数据库端提取记录量,一般无需设置
	 */
	private int fetchSize = -1;

	/**
	 * 自动根据POJO创建或更新表
	 */
	private Boolean autoDDL = false;

	/**
	 * 分页最大单页数据量(默认是5万)
	 */
	private Integer pageFetchSizeLimit;

	/**
	 * 超时打印sql(毫秒,默认30秒)
	 */
	private Integer printSqlTimeoutMillis;

	/**
	 * sql文件脚本变更检测间隔时长(秒)
	 */
	private Integer scriptCheckIntervalSeconds;

	/**
	 * 缓存更新、sql脚本更新 延迟多少秒开始检测
	 */
	private Integer delayCheckSeconds;

	private String encoding;

	/**
	 * 统一字段处理器
	 */
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
	private String reservedWords;

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
	private String cacheType = "ehcache";

	/**
	 * 当发现有重复sqlId时是否抛出异常，终止程序执行
	 */
	private boolean breakWhenSqlRepeat = true;

	/**
	 * map类型的resultType标题转驼峰模式(默认为true)
	 */
	private Boolean humpMapResultTypeLabel;

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
	 * 自定义sql拦截加工处理器
	 */
	private String[] sqlInterceptors;

	/**
	 * 拆分merge into 为updateAll 和 saveAllIgnoreExist 两步操作(1、seata分布式事务不支持merge)
	 */
	private boolean splitMergeInto = false;

	/**
	 * 数据修改提示的记录数量阈值，默认2000条
	 */
	private int updateTipCount = 2000;

	/**
	 * executeSql变更操作型sql执行空白参数是否默认转为null
	 */
	private boolean executeSqlBlankToNull = true;

	/**
	 * 跳转超出数据页范围回到第一页
	 */
	private Boolean overPageToFirst;

	/**
	 * sql格式化输出器(用于debug sql输出)
	 */
	private String sqlFormater;

	/**
	 * 未匹配的数据库类型分页是否是limit ? offset ? 模式还是 limit ?,? 模式
	 */
	private boolean defaultPageOffset = true;

	/**
	 * 线程池配置参数
	 */
	private SqlToyContextTaskPoolProperties taskExecutor = new SqlToyContextTaskPoolProperties();

	/**
	 * 默认一页数据记录数量
	 */
	private int defaultPageSize = 10;
}
