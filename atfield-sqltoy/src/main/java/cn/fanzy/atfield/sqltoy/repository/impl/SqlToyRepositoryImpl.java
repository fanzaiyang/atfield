package cn.fanzy.atfield.sqltoy.repository.impl;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.core.utils.IdUtils;
import cn.fanzy.atfield.sqltoy.delflag.context.DelFlagContext;
import cn.fanzy.atfield.sqltoy.delflag.enums.DeleteValueStrategyEnum;
import cn.fanzy.atfield.sqltoy.delflag.service.DeleteValueGenerateService;
import cn.fanzy.atfield.sqltoy.entity.ParamBatchDto;
import cn.fanzy.atfield.sqltoy.mp.IPage;
import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import cn.fanzy.atfield.sqltoy.utils.IdWorker;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.sagframe.sagacity.sqltoy.plus.conditions.Wrappers;
import com.sagframe.sagacity.sqltoy.plus.conditions.toolkit.StringPool;
import com.sagframe.sagacity.sqltoy.plus.dao.SqlToyHelperDaoImpl;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.model.EntityUpdate;
import org.sagacity.sqltoy.model.MapKit;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.model.TreeTableModel;
import org.sagacity.sqltoy.translate.TranslateManager;
import org.sagacity.sqltoy.translate.model.CheckerConfigModel;
import org.sagacity.sqltoy.translate.model.TranslateConfigModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础存储 库实现类
 *
 * @author fanzaiyang
 * @date 2024-07-01
 */
@RequiredArgsConstructor
public class SqlToyRepositoryImpl extends SqlToyHelperDaoImpl implements SqlToyRepository {
    private final SqltoyExtraProperties properties;

    @Override
    public <T> T findOne(String sql, Map<String, Object> param, Class<T> clazz) {
        List<T> list = findBySql(sql, param, clazz);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public <T> T findOne(String sql, Map<String, Object> param, Class<T> clazz, boolean multiException) {
        List<T> list = findBySql(sql, param, clazz);
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        Assert.isTrue(CollUtil.size(list) == 1, "查询到多个结果！");
        return list.get(0);
    }

    @Override
    public void handleUpdateStatus(Class<?> entityClass, ParamBatchDto param) {
        update(Wrappers.updateWrapper(entityClass)
                .set("status", param.getNextStatus())
                .in("id", param.getTargets()));
    }

    @Override
    public void handleUpdateTreeStatus(Class<?> entityClass, ParamBatchDto param) {
        Assert.notNull(param.getNextStatus(), "状态不能为空！");
        EntityMeta entityMeta = getEntityMeta(entityClass);
        String tableName = entityMeta.getTableName();
        executeSql("""
                update @value(:tableName) set status = :nextStatus where node_route regexp :ids
                """, MapKit.keys("tableName", "nextStatus", "ids")
                .values(tableName, param.getNextStatus(),
                        String.join("|", param.getTargets())));
    }

    @Override
    public void handleUpdateTreeDelete(Class<?> entityClass, ParamBatchDto param) {
        String deleteValue = param.getNextStatus() == null ? getLogicDeletedValue(entityClass) : param.getNextStatus().toString();
        String deleteField = StrUtil.blankToDefault(DelFlagContext.getDeleteField(), properties.getLogicDeleteField());
        EntityMeta entityMeta = getEntityMeta(entityClass);
        String tableName = entityMeta.getTableName();

        executeSql("""
                update @value(:tableName) set @value(:deleteField) = :nextStatus where node_route regexp :ids
                """, MapKit.keys("tableName", "deleteField", "nextStatus", "ids")
                .values(tableName,
                        deleteField,
                        deleteValue,
                        String.join("|", param.getTargets())));
    }

    @Override
    public void handleUpdateDelete(Class<?> entityClass, ParamBatchDto param) {
        String deleteField = StrUtil.blankToDefault(DelFlagContext.getDeleteField(), properties.getLogicDeleteField());
        String deleteValue = param.getNextStatus() == null ? getLogicDeletedValue(entityClass) : param.getNextStatus().toString();
        update(Wrappers.updateWrapper(entityClass)
                .set(deleteField, deleteValue)
                .in("id", param.getTargets()));
    }

    @Override
    public boolean wrapTreeTableRoute(Serializable entity) {
        return super.wrapTreeTableRoute(new TreeTableModel(entity)
                .pidField("parentId"));
    }

    @Override
    public boolean wrapTreeTableRoute(Serializable entity, String pidField) {
        return super.wrapTreeTableRoute(new TreeTableModel(entity)
                .pidField(pidField));
    }

    @Override
    public boolean wrapTreeTableRoute(List<Serializable> entities) {
        for (Serializable entity : entities) {
            wrapTreeTableRoute(entity);
        }
        return true;
    }

    @Override
    public <T> boolean wrapTreeTableRouteName(Class<T> entityClass, String fieldName, String targetFieldName) {
        Assert.notBlank(fieldName, "字段名不能为空!");
        Assert.notBlank(targetFieldName, "目标字段名不能为空!");
        EntityMeta entityMeta = getEntityMeta(entityClass);
        String tableName = entityMeta.getTableName();
        executeSql(
                """
                        UPDATE @value(:tableName) o
                         INNER JOIN (
                           SELECT
                             t.id,
                             GROUP_CONCAT(p.@value(:fieldName) ORDER BY p.node_level SEPARATOR '/') as full_name
                           FROM
                             @value(:tableName) t
                             LEFT JOIN @value(:tableName) p ON find_in_set(p.id,t.node_route) and p.del_flag=0
                           GROUP BY
                             t.id) tr ON tr.id = o.id
                         SET o.@value(:targetFieldName) = tr.full_name
                         where o.del_flag=0
                        """,
                MapKit.keys("tableName", "fieldName", "targetFieldName")
                        .values(tableName, fieldName, targetFieldName));
        return true;
    }

    @Override
    public <T extends Serializable> IPage<T> convert(Page<T> sourcePage) {
        IPage<T> iPage = new cn.fanzy.atfield.sqltoy.mp.Page<>();
        iPage.setTotal(sourcePage.getRecordCount());
        iPage.setCurrent(sourcePage.getPageNo());
        iPage.setSize(sourcePage.getPageSize());
        iPage.setRecords(sourcePage.getRows());
        iPage.setPages(sourcePage.getTotalPage());
        return iPage;
    }

    @Override
    public <T> Long remove(Class<T> clazz, Object... ids) {
        EntityMeta meta = getEntityMeta(clazz);
        String deleteField = StrUtil.blankToDefault(DelFlagContext.getDeleteField(), properties.getLogicDeleteField());
        String deleteValue = getLogicDeletedValue(clazz);
        String whereSql = meta.getIdArgWhereSql();
        if (StrUtil.startWithIgnoreCase(whereSql, "where")) {
            whereSql = whereSql.substring(6);
        }
        return updateByQuery(clazz, EntityUpdate.create()
                .set(deleteField, deleteValue)
                .where(whereSql)
                .values(ids));
    }

    @Override
    public TranslateManager getTranslateManager() {
        return getSqlToyContext().getTranslateManager();
    }

    @Override
    public void addCache(String cacheName, String sql) {
        addCache(cacheName, sql, false);
    }

    @Override
    public void addCache(String cacheName, String sql, boolean forceUpdate) {
        if (!forceUpdate) {
            boolean existed = getTranslateManager().existCache(cacheName);
            if (existed) {
                return;
            }
        }
        sql = StrUtil.replace(sql, "select", "SELECT");
        sql = StrUtil.replace(sql, "from", "FROM");
        TranslateConfigModel model = new TranslateConfigModel();
        model.setCache(cacheName);
        model.setType("sql");
        model.setSql(sql);
        String pattern = "SELECT(.*?)FROM";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(sql);
        if (m.find()) {
            String columns = m.group(1);
            model.setProperties(StrUtil.splitToArray(columns, StringPool.COMMA));
        }

        getTranslateManager().putCache(model);
    }

    @Override
    public void addCacheChecker(String cacheName, String sql, Boolean increment, Integer checkFrequency) {
        CheckerConfigModel model = new CheckerConfigModel();
        model.setIncrement(increment == null || increment);
        model.setCache(cacheName);
        model.setSql(sql);
        // 15秒
        model.setCheckFrequency(checkFrequency == null ? "15" : checkFrequency.toString());
        getTranslateManager().putCacheUpdater(model);
    }

    @Override
    public <T> String getLogicDeletedValue(Class<T> clazz) {
        String deleteValue = StrUtil.blankToDefault(DelFlagContext.getDeleteValue(), properties.getLogicDeleteValue());
        if (!DeleteValueStrategyEnum.STATIC.equals(properties.getDeleteValueStrategy())) {
            if (DeleteValueStrategyEnum.UUID.equals(properties.getDeleteValueStrategy())) {
                deleteValue = IdUtils.randomUUID();
            } else if (DeleteValueStrategyEnum.UUID_SIMPLE.equals(properties.getDeleteValueStrategy())) {
                deleteValue = IdUtils.simpleUUID();
            } else if (DeleteValueStrategyEnum.SNOWFLAKE.equals(properties.getDeleteValueStrategy())) {
                deleteValue = IdUtils.getSnowflakeNextIdStr();
            } else if (DeleteValueStrategyEnum.SNOWFLAKE_SIMPLE.equals(properties.getDeleteValueStrategy())) {
                deleteValue = IdWorker.nextSnowId();
            } else if (DeleteValueStrategyEnum.CUSTOM.equals(properties.getDeleteValueStrategy())) {
                try {
                    DeleteValueGenerateService valueGenerateService = SpringUtils.getBean(DeleteValueGenerateService.class);
                    deleteValue = valueGenerateService.generate(clazz);
                    Assert.notBlank(deleteValue, "自定义删除值生成服务未返回有效值");
                } catch (Exception e) {
                    throw new RuntimeException("自定义删除值生成服务未配置或未实现接口：" + DeleteValueGenerateService.class.getName());
                }
            }
        }
        return deleteValue;
    }
}
