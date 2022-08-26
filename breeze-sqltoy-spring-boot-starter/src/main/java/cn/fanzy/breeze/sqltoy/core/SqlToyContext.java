package cn.fanzy.breeze.sqltoy.core;

import cn.fanzy.breeze.sqltoy.config.SqlToyContextProperties;
import cn.fanzy.breeze.sqltoy.core.config.EntityManager;
import cn.fanzy.breeze.sqltoy.core.config.SqlConfigParseUtils;
import cn.fanzy.breeze.sqltoy.core.config.SqlScriptLoader;
import cn.fanzy.breeze.sqltoy.core.config.model.EntityMeta;
import cn.fanzy.breeze.sqltoy.core.config.model.SqlToyConfig;
import cn.fanzy.breeze.sqltoy.core.config.model.SqlType;
import cn.fanzy.breeze.sqltoy.core.integration.AppContext;
import cn.fanzy.breeze.sqltoy.core.integration.ConnectionFactory;
import cn.fanzy.breeze.sqltoy.core.integration.DistributeIdGenerator;
import cn.fanzy.breeze.sqltoy.core.integration.MongoQuery;
import cn.fanzy.breeze.sqltoy.core.model.QueryExecutor;
import cn.fanzy.breeze.sqltoy.core.plugins.FilterHandler;
import cn.fanzy.breeze.sqltoy.core.plugins.UnifyFieldsHandler;
import cn.fanzy.breeze.sqltoy.core.plugins.OverTimeSqlHandler;
import cn.fanzy.breeze.sqltoy.core.plugins.TypeHandler;
import cn.fanzy.breeze.sqltoy.core.plugins.datasource.DataSourceSelector;
import cn.fanzy.breeze.sqltoy.core.plugins.function.FunctionUtils;
import cn.fanzy.breeze.sqltoy.core.plugins.secure.DesensitizeProvider;
import cn.fanzy.breeze.sqltoy.core.plugins.secure.FieldsSecureProvider;
import cn.fanzy.breeze.sqltoy.core.plugins.sharding.ShardingStrategy;
import cn.fanzy.breeze.sqltoy.core.translate.TranslateManager;
import cn.fanzy.breeze.sqltoy.core.utils.BeanUtil;
import cn.fanzy.breeze.sqltoy.core.utils.ReservedWordsUtil;
import cn.fanzy.breeze.sqltoy.core.utils.SqlUtil;
import cn.fanzy.breeze.sqltoy.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//------------------了解 sqltoy的关键优势: -------------------------------------------------------------------------------------------*/
//1、最简最直观的sql编写方式(不仅仅是查询语句)，采用条件参数前置处理规整法，让sql语句部分跟客户端保持高度一致
//2、sql中支持注释(规避了对hint特性的影响,知道hint吗?搜oracle hint)，和动态更新加载，便于开发和后期维护整个过程的管理
//3、支持缓存翻译和反向缓存条件检索(通过缓存将名称匹配成精确的key)，实现sql简化和性能大幅提升
//4、支持快速分页和分页优化功能，实现分页最高级别的优化，同时还考虑到了cte多个with as情况下的优化支持
//5、支持并行查询
//6、根本杜绝sql注入问题
//7、支持行列转换、分组汇总求平均、同比环比计算，在于用算法解决复杂sql，同时也解决了sql跨数据库问题
//8、支持保留字自动适配
//9、支持跨数据库函数自适配,从而非常有利于一套代码适应多种数据库便于产品化,比如oracle的nvl，当sql在mysql环境执行时自动替换为ifnull
//10、支持分库分表
//11、提供了在新增和修改操作过程中公共字段插入和修改，如:租户、创建人、创建时间、修改时间等
//12、提供了统一数据权限传参和数据越权校验
//13、提供了取top、取random记录、树形表结构构造和递归查询支持、updateSaveFetch/updateFetch单次交互完成修改和查询等实用的功能
//14、sqltoy的update、save、saveAll、load 等crud操作规避了jpa的缺陷,参见update(entity,String...forceUpdateProps)和updateFetch、updateSaveFetch
//15、提供了极为人性化的条件处理:排它性条件、日期条件加减和提取月末月初处理等
//16、提供了查询结果日期、数字格式化、安全脱敏处理，让复杂的事情变得简单，大幅简化sql和结果的二次处理工作
//------------------------------------------------------------------------------------------------------------------------------------*/

/**
 * @author zhongxuchen
 * @version v1.0, Date:2009-12-11
 * @project sagacity-sqltoy
 * @description sqltoy 工具的上下文容器，提供对应的sql获取以及相关参数设置
 * @modify {Date:2018-1-5,增加对redis缓存翻译的支持}
 * @modify {Date:2019-09-15,将跨数据库函数FunctionConverts统一提取到FunctionUtils中,实现不同数据库函数替换后的语句放入缓存,避免每次执行函数替换}
 * @modify {Date:2020-05-29,调整mongo的注入方式,剔除之前MongoDbFactory模式,直接使用MongoTemplate}
 * @modify {Date:2022-04-23,pageOverToFirst默认值改为false}
 * @modify {Date:2022-06-11,支持多个缓存翻译定义文件}
 * @modify {Date:2022-06-14,剔除pageOverToFirst 属性}
 */
@Slf4j
@AllArgsConstructor
public class SqlToyContext {
    private final SqlToyContextProperties properties;
    private final AppContext appContext;
    /**
     * sqlToy 配置解析插件
     */
    private final SqlScriptLoader scriptLoader;

    /**
     * 实体对象管理器，加载实体bean
     */
    private final EntityManager entityManager;

    /**
     * sqltoy的翻译器插件(可以通过其完成对缓存的管理扩展)
     */
    private final TranslateManager translateManager;


    /**
     * 自定义参数过滤处理器(防范性预留)
     */
    private final FilterHandler customFilterHandler;

    /**
     * 执行超时sql自定义处理器
     */
    private final OverTimeSqlHandler overTimeSqlHandler;
    /**
     * 提供自定义类型处理器,一般针对json等类型
     */
    private final TypeHandler typeHandler;
    /**
     * 字段加解密实现类，sqltoy提供了RSA的默认实现
     */
    private final FieldsSecureProvider fieldsSecureProvider;
    /**
     * 脱敏处理器
     */
    private final DesensitizeProvider desensitizeProvider;

    /**
     * 提供数据源获得connection的扩展(默认spring的实现)
     */
    private final ConnectionFactory connectionFactory;

    /**
     * dataSource选择器，提供给开发者扩展窗口
     */
    private final DataSourceSelector dataSourceSelector;
    /**
     * sharding策略
     */
    private Map<String, ShardingStrategy> shardingStrategys = new HashMap<>();

    /**
     * 默认数据库连接池
     */
    private DataSource defaultDataSource;
    /**
     * 分布式id生成器
     */
    private final DistributeIdGenerator distributeIdGenerator;

    private final UnifyFieldsHandler unifyFieldsHandler;

    private final MongoQuery mongoQuery;

    /**
     * @throws Exception
     * @todo 初始化
     */
    public void initialize() throws Exception {
        log.debug("start init sqltoy ..............................");
        // 加载sqltoy的各类参数,如db2是否要增加with
        // ur等,详见org/sagacity/sqltoy/sqltoy-default.properties
        SqlToyConstants.loadProperties(properties.getDialectConfig());
        // 设置workerId和dataCenterId,为使用snowflake主键ID产生算法服务
        SqlToyConstants.setWorkerAndDataCenterId(properties.getSnowflake().getWorkerId(), properties.getSnowflake().getDataCenterId(), properties.getSnowflake().getServerId());

        // 初始化脚本加载器
        scriptLoader.initialize(properties);

        // 初始化实体对象管理器(此功能已经无实际意义,已经改为即用即加载而非提前加载)
        entityManager.initialize(this);
        // 设置保留字
        ReservedWordsUtil.put(properties.getReservedWords());
        // 设置默认fetchSize
        SqlToyConstants.FETCH_SIZE = properties.getFetchSize();
        // 初始化sql执行统计的基本参数
        SqlExecuteStat.setDebug(properties.isDebug());
        SqlExecuteStat.setOverTimeSqlHandler(overTimeSqlHandler);
        SqlExecuteStat.setPrintSqlTimeoutMillis(properties.getPrintSqlTimeoutMillis());
        log.debug("sqltoy init complete!");
    }

    /**
     * @param beanName
     * @param method
     * @param args
     * @return
     * @todo 获取service并调用其指定方法获取报表数据
     */
    public Object getServiceData(String beanName, String method, Object[] args) {
        if (StringUtil.isBlank(beanName) || StringUtil.isBlank(method)) {
            return null;
        }
        try {
            Object beanDefine = null;
            if (appContext.containsBean(beanName)) {
                beanDefine = appContext.getBean(beanName);
            } else if (beanName.indexOf(".") > 0) {
                beanDefine = appContext.getBean(Class.forName(beanName));
            } else {
                return null;
            }
            return BeanUtil.invokeMethod(beanDefine, method, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param beanName
     * @return
     * @todo 获取bean
     */
    public Object getBean(Object beanName) {
        try {
            if (beanName instanceof String) {
                return appContext.getBean(beanName.toString());
            }
            return appContext.getBean((Class) beanName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("从springContext中获取Bean:{} 错误!{}", e.getMessage());
        }
        return null;
    }

    /**
     * 是否是一个SpringBean
     *
     * @param beanName bean名字
     * @return boolean
     */
    public boolean isBean(Object beanName) {
        try {
            if (beanName instanceof String) {
                return appContext.containsBean(beanName.toString());
            } else {
                appContext.getBean((Class) beanName);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param dataSourceName
     * @return
     * @todo 获取数据源
     */
    public DataSource getDataSourceBean(String dataSourceName) {
        if (StringUtil.isBlank(dataSourceName)) {
            return null;
        }
        // 优先使用扩展来实现
        if (dataSourceSelector != null) {
            return dataSourceSelector.getDataSourceBean(appContext, dataSourceName);
        } else if (appContext.containsBean(dataSourceName)) {
            return (DataSource) appContext.getBean(dataSourceName);
        }
        return null;
    }

    /**
     * @param sqlKey
     * @return
     * @TODO 保留一个获取查询的sql(针对报表平台)
     */
    public SqlToyConfig getSqlToyConfig(String sqlKey) {
        return getSqlToyConfig(sqlKey, SqlType.search, (getDialect() == null) ? "" : getDialect());
    }

    /**
     * @param sqlKey
     * @param sqlType
     * @param dialect
     * @return
     * @todo 获取sql对应的配置模型(请阅读scriptLoader, 硬code的sql对应模型也利用了内存来存放非每次都动态构造对象)
     */
    public SqlToyConfig getSqlToyConfig(String sqlKey, SqlType sqlType, String dialect) {
        if (StringUtil.isBlank(sqlKey)) {
            throw new IllegalArgumentException("sql or sqlId is null!");
        }
        return scriptLoader.getSqlConfig(sqlKey, sqlType, dialect);
    }

    public SqlToyConfig getSqlToyConfig(QueryExecutor queryExecutor, SqlType sqlType, String dialect) {
        String sqlKey = queryExecutor.getInnerModel().sql;
        if (StringUtil.isBlank(sqlKey)) {
            throw new IllegalArgumentException("sql or sqlId is null!");
        }
        // 查询语句补全select * from table,避免一些sql直接从from 开始
        if (SqlType.search.equals(sqlType)) {
            if (queryExecutor.getInnerModel().resultType != null) {
                sqlKey = SqlUtil.completionSql(this, (Class) queryExecutor.getInnerModel().resultType, sqlKey);
            } // update 2021-12-7 sql 类似 from table where xxxx 形式，补全select *
            else if (!SqlConfigParseUtils.isNamedQuery(sqlKey)
                    && StringUtil.matches(sqlKey.toLowerCase().trim(), "^from\\W")) {
                sqlKey = "select * ".concat(sqlKey);
            }
        }
        SqlToyConfig result = scriptLoader.getSqlConfig(sqlKey, sqlType, dialect);
        // 剔除空白转null的默认设置
        if (!queryExecutor.getInnerModel().blankToNull && StringUtil.isBlank(result.getId())) {
            if (result.getFilters().size() == 1) {
                result.getFilters().remove(0);
            }
        }
        return result;
    }



    /**
     * @return the batchSize
     */
    public int getBatchSize() {
        return properties.getBatchSize();
    }


    /**
     * @param strategyName
     * @return
     * @todo 返回sharding策略实例
     */
    public ShardingStrategy getShardingStrategy(String strategyName) {
        // hashMap可以事先不赋值,直接定义spring的bean
        if (shardingStrategys.containsKey(strategyName)) {
            return shardingStrategys.get(strategyName);
        }
        ShardingStrategy shardingStrategy = (ShardingStrategy) appContext.getBean(strategyName);
        if (shardingStrategy != null) {
            shardingStrategys.put(strategyName, shardingStrategy);
        }
        return shardingStrategy;
    }


    public EntityMeta getEntityMeta(Class<?> entityClass) {
        return entityManager.getEntityMeta(this, entityClass);
    }

    /**
     * @param entityClass
     * @return
     * @TODO 判断是否是实体bean
     */
    public boolean isEntity(Class<?> entityClass) {
        return entityManager.isEntity(this, entityClass);
    }

    /**
     * <p>
     * <li>1、第一步调用解析，注意是单个sqlId的片段</li>
     * <li>2、根据业务情况，调整id,sqlToyConfig.setId(),注意:这步并非必要,当报表平台时,报表里面多个sql,每个id在本报表范围内唯一，当很多个报表时会冲突，所以需要整合rptId+sqlId</li>
     * <li>3、putSqlToyConfig(SqlToyConfig sqlToyConfig) 放入交由sqltoy统一管理</li>
     * </p>
     *
     * @param sqlSegment
     * @return
     * @throws Exception
     * @todo 提供可以动态增加解析sql片段配置的接口, 完成SqltoyConfig模型的构造(用于第三方平台集成 ， 如报表平台等)，
     */
    public synchronized SqlToyConfig parseSqlSegment(Object sqlSegment) throws Exception {
        return scriptLoader.parseSqlSagment(sqlSegment);
    }

    /**
     * @param sqlToyConfig
     * @throws Exception
     * @todo 将构造好的SqlToyConfig放入交给sqltoy统一托管(在托管前可以对id进行重新组合确保id的唯一性, 比如报表平台 ， 将rptId + sqlId组合成一个全局唯一的id)
     */
    public synchronized void putSqlToyConfig(SqlToyConfig sqlToyConfig) throws Exception {
        scriptLoader.putSqlToyConfig(sqlToyConfig);
    }

    /**
     * @param sqlFile
     * @throws Exception
     * @TODO 开放sql文件动态交由开发者挂载
     */
    public synchronized void parseSqlFile(Object sqlFile) throws Exception {
        scriptLoader.parseSqlFile(sqlFile);
    }

    /**
     * @return the dialect
     */
    public String getDialect() {
        return properties.getDialect();
    }



    /**
     * @return the debug
     */
    public boolean isDebug() {
        return properties.isDebug();
    }


    /**
     * @return the pageFetchSizeLimit
     */
    public int getPageFetchSizeLimit() {
        return properties.getPageFetchSizeLimit();
    }


    /**
     * functionConverts=close表示关闭
     *
     * @param functionConverts the functionConverts to set
     */
    public void setFunctionConverts(Object functionConverts) {
        if (functionConverts == null) {
            return;
        }
        if (functionConverts instanceof List) {
            FunctionUtils.setFunctionConverts((List<String>) functionConverts);
        } else if (functionConverts instanceof String) {
            String converts = (String) functionConverts;
            if (StringUtil.isBlank(converts) || "default".equals(converts) || "defaults".equals(converts)) {
                FunctionUtils.setFunctionConverts(null);
            } else if (!"close".equalsIgnoreCase(converts)) {
                FunctionUtils.setFunctionConverts(Arrays.asList(converts.split("\\,")));
            }
        }
    }


    public AppContext getAppContext() {
        return appContext;
    }



    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }


    /**
     * @return the typeHandler
     */
    public TypeHandler getTypeHandler() {
        return typeHandler;
    }


    public void destroy() {
        try {
            scriptLoader.destroy();
            translateManager.destroy();
        } catch (Exception e) {

        }
    }

    /**
     * @return the dataSourceSelector
     */
    public DataSourceSelector getDataSourceSelector() {
        return dataSourceSelector;
    }

    public Connection getConnection(DataSource datasource) {
        return connectionFactory.getConnection(datasource);
    }

    public void releaseConnection(Connection conn, DataSource dataSource) {
        connectionFactory.releaseConnection(conn, dataSource);
    }

    public TranslateManager getTranslateManager() {
        return translateManager;
    }

    public FieldsSecureProvider getFieldsSecureProvider() {
        return fieldsSecureProvider;
    }

    public DesensitizeProvider getDesensitizeProvider() {
        return desensitizeProvider;
    }

    public FilterHandler getCustomFilterHandler() {
        return customFilterHandler;
    }



    public String getColumnLabelUpperOrLower() {
        return properties.getColumnLabelUpperOrLower();
    }


    public String[] getRedoDataSources() {
        return properties.getRedoDataSources();
    }

    public DistributeIdGenerator getDistributeIdGenerator() {
        return distributeIdGenerator;
    }

    public UnifyFieldsHandler getUnifyFieldsHandler() {
        return unifyFieldsHandler;
    }

    public MongoQuery getMongoQuery() {
        return mongoQuery;
    }
}
