package cn.fanzy.breeze.sqltoy.config;

import cn.fanzy.breeze.sqltoy.core.SqlToyContext;
import cn.fanzy.breeze.sqltoy.core.config.EntityManager;
import cn.fanzy.breeze.sqltoy.core.config.SqlScriptLoader;
import cn.fanzy.breeze.sqltoy.core.dao.SqlToyLazyDao;
import cn.fanzy.breeze.sqltoy.core.dao.impl.SqlToyLazyDaoImpl;
import cn.fanzy.breeze.sqltoy.core.integration.AppContext;
import cn.fanzy.breeze.sqltoy.core.integration.ConnectionFactory;
import cn.fanzy.breeze.sqltoy.core.integration.DistributeIdGenerator;
import cn.fanzy.breeze.sqltoy.core.integration.MongoQuery;
import cn.fanzy.breeze.sqltoy.core.integration.impl.SpringAppContext;
import cn.fanzy.breeze.sqltoy.core.integration.impl.SpringConnectionFactory;
import cn.fanzy.breeze.sqltoy.core.integration.impl.SpringMongoQuery;
import cn.fanzy.breeze.sqltoy.core.integration.impl.SpringRedisIdGenerator;
import cn.fanzy.breeze.sqltoy.core.plugins.*;
import cn.fanzy.breeze.sqltoy.core.plugins.datasource.DataSourceSelector;
import cn.fanzy.breeze.sqltoy.core.plugins.datasource.impl.DefaultDataSourceSelector;
import cn.fanzy.breeze.sqltoy.core.plugins.id.IdGenerator;
import cn.fanzy.breeze.sqltoy.core.plugins.id.impl.*;
import cn.fanzy.breeze.sqltoy.core.plugins.overtime.DefaultOverTimeHandler;
import cn.fanzy.breeze.sqltoy.core.plugins.secure.DesensitizeProvider;
import cn.fanzy.breeze.sqltoy.core.plugins.secure.FieldsSecureProvider;
import cn.fanzy.breeze.sqltoy.core.plugins.secure.impl.DesensitizeDefaultProvider;
import cn.fanzy.breeze.sqltoy.core.plugins.secure.impl.FieldsRSASecureProvider;
import cn.fanzy.breeze.sqltoy.core.service.SqlToyCRUDService;
import cn.fanzy.breeze.sqltoy.core.service.impl.SqlToyCRUDServiceImpl;
import cn.fanzy.breeze.sqltoy.core.translate.TranslateManager;
import cn.fanzy.breeze.sqltoy.core.translate.cache.TranslateCacheManager;
import cn.fanzy.breeze.sqltoy.core.translate.cache.impl.TranslateCaffeineManager;
import cn.fanzy.breeze.sqltoy.core.translate.cache.impl.TranslateEhcacheManager;
import cn.fanzy.breeze.sqltoy.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author wolf
 * @version v1.0, Date:2018年12月26日
 * @description sqltoy 自动配置类
 * @modify {Date:2020-2-20,完善配置支持es等,实现完整功能}
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(SqlToyContextProperties.class)
public class SqltoyAutoConfiguration {
    private final SqlToyContextProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public AppContext appContext(ApplicationContext applicationContext) {
        return new SpringAppContext(applicationContext);
    }

    /**
     * sqlToy 配置解析插件
     *
     * @return {@link SqlScriptLoader}
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlScriptLoader scriptLoader() {
        return new SqlScriptLoader();
    }

    /**
     * 实体对象管理器，加载实体bean
     *
     * @return {@link EntityManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public EntityManager entityManager() {
        return new EntityManager();
    }

    /**
     * sqltoy的翻译器插件(可以通过其完成对缓存的管理扩展)
     *
     * @return {@link TranslateManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public TranslateManager translateManager() {
        return new TranslateManager(properties);
    }

    /**
     * 统一字段处理程序
     * <pre>统一公共字段赋值处理; 如修改时,为修改人和修改时间进行统一赋值; 创建时:为创建人、创建时间、修改人、修改时间进行统一赋值</pre>
     *
     * @return {@link UnifyFieldsHandler}
     */
    @Bean
    @ConditionalOnMissingBean
    public UnifyFieldsHandler unifyFieldsHandler() {
        return new DefaultUnifyFieldsHandler();
    }

    /**
     * 基于ehcache缓存实现translate 提取缓存数据和存放缓存
     *
     * @return {@link TranslateCacheManager}
     */
    @Bean
    @ConditionalOnClass(name = "org.ehcache.config.ResourceUnit")
    @ConditionalOnProperty(prefix = "spring.sqltoy", name = "cacheType", havingValue = "ehcache", matchIfMissing = true)
    @ConditionalOnMissingBean
    public TranslateCacheManager translateEhcacheManager() {
        return new TranslateEhcacheManager();
    }

    /**
     * 提供基于Caffeine缓存实现
     *
     * @return {@link TranslateCacheManager}
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.sqltoy", name = "cacheType", havingValue = "caffeine")
    @ConditionalOnMissingBean
    public TranslateCacheManager translateCacheManager() {
        return new TranslateCaffeineManager();
    }

    /**
     * 执行超时sql自定义处理器
     *
     * @return {@link OverTimeSqlHandler}
     */
    @Bean
    @ConditionalOnMissingBean
    public OverTimeSqlHandler overTimeSqlHandler() {
        return new DefaultOverTimeHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterHandler filterHandler() {
        return new FilterHandler() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public TypeHandler typeHandler() {
        return new TypeHandler() {
            @Override
            public boolean setValue(PreparedStatement pst, int paramIndex, int jdbcType, Object value) throws SQLException {
                return false;
            }

            @Override
            public Object toJavaType(String javaTypeName, Class genericType, Object jdbcValue) throws Exception {
                return null;
            }
        };
    }

    /**
     * 字段加解密实现类，sqltoy提供了RSA的默认实现
     *
     * @return {@link FieldsSecureProvider}
     * @throws Exception 异常
     */
    @Bean
    @ConditionalOnMissingBean
    public FieldsSecureProvider fieldsSecureProvider() throws Exception {
        if (StringUtil.isBlank(properties.getSecurePrivateKey()) || StringUtil.isBlank(properties.getSecurePublicKey())) {
            return null;
        }
        return new FieldsRSASecureProvider(properties);
    }

    /**
     * 脱敏处理器
     *
     * @return {@link DesensitizeProvider}
     */
    @Bean
    @ConditionalOnMissingBean
    public DesensitizeProvider desensitizeProvider() {
        return new DesensitizeDefaultProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory() {
        return new SpringConnectionFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceSelector dataSourceSelector() {
        return new DefaultDataSourceSelector();
    }

    @Bean
    @ConditionalOnMissingBean
    public DistributeIdGenerator distributeIdGenerator() {
        return new SpringRedisIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoQuery mongoQuery() {
        try {
            Class.forName("org.springframework.data.mongodb.core.query.Query");
            return new SpringMongoQuery();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Bean(name = "sqlToyContext", initMethod = "initialize", destroyMethod = "destroy")
    @ConditionalOnMissingBean
    SqlToyContext sqlToyContext(SqlToyContextProperties properties, AppContext appContext, SqlScriptLoader scriptLoader, EntityManager entityManager,
                                TranslateManager translateManager, FilterHandler filterHandler, OverTimeSqlHandler overTimeSqlHandler,
                                TypeHandler typeHandler, FieldsSecureProvider fieldsSecureProvider, DesensitizeProvider desensitizeProvider,
                                ConnectionFactory connectionFactory, DataSourceSelector dataSourceSelector, DistributeIdGenerator distributeIdGenerator,
                                UnifyFieldsHandler unifyFieldsHandler, MongoQuery mongoQuery) throws Exception {
        // 用辅助配置来校验是否配置错误
        if (StringUtil.isBlank(properties.getSqlResourcesDir())) {
            throw new IllegalArgumentException(
                    "请检查sqltoy配置,是spring.sqltoy作为前缀,而不是sqltoy!\n正确范例: spring.sqltoy.sqlResourcesDir=classpath:com/sagframe/modules");
        }
        SqlToyContext sqlToyContext = new SqlToyContext(properties, appContext, scriptLoader, entityManager,
                translateManager, filterHandler, overTimeSqlHandler,
                typeHandler, fieldsSecureProvider, desensitizeProvider, connectionFactory,
                dataSourceSelector, new HashMap<>(), null, distributeIdGenerator, unifyFieldsHandler, mongoQuery);

        // sql函数转换器
        if (properties.getFunctionConverts() != null) {
            sqlToyContext.setFunctionConverts(properties.getFunctionConverts());
        }
        return sqlToyContext;
    }

    /**
     * 5.2 版本要注入sqlToyContext
     *
     * @return 返回预定义的通用Dao实例
     */
    @Bean(name = "sqlToyLazyDao")
    @ConditionalOnMissingBean
    SqlToyLazyDao sqlToyLazyDao(SqlToyContext sqlToyContext) {
        SqlToyLazyDaoImpl lazyDao = new SqlToyLazyDaoImpl();
        lazyDao.setSqlToyContext(sqlToyContext);
        return lazyDao;
    }

    /**
     * 5.2 版本要注入sqlToyLazyDao
     *
     * @return 返回预定义的通用CRUD service实例
     */
    @Bean(name = "sqlToyCRUDService")
    @ConditionalOnMissingBean
    SqlToyCRUDService sqlToyCRUDService(SqlToyLazyDao sqlToyLazyDao) {
        SqlToyCRUDServiceImpl sqlToyCRUDService = new SqlToyCRUDServiceImpl();
        sqlToyCRUDService.setSqlToyLazyDao(sqlToyLazyDao);
        return sqlToyCRUDService;
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultIdGenerator defaultIdGenerator() {
        return new DefaultIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public NanoTimeIdGenerator nanoTimeIdGenerator() {
        return new NanoTimeIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisIdGenerator redisIdGenerator() {
        return new RedisIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    public UUIDGenerator uUIDGenerator() {
        return new UUIDGenerator();
    }
}
