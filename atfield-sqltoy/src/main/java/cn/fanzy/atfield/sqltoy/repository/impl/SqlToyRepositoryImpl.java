package cn.fanzy.atfield.sqltoy.repository.impl;

import cn.fanzy.atfield.sqltoy.entity.ParamBatchDto;
import cn.fanzy.atfield.sqltoy.mp.IPage;
import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
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
        EntityMeta entityMeta = getEntityMeta(entityClass);
        String tableName = entityMeta.getTableName();
        executeSql("""
                update @value(:tableName) set del_flag = :nextStatus where node_route regexp :ids
                """, MapKit.keys("tableName", "nextStatus", "ids")
                .values(tableName, param.getNextStatus(),
                        String.join("|", param.getTargets())));
    }

    @Override
    public void handleUpdateDelete(Class<?> entityClass, ParamBatchDto param) {
        update(Wrappers.updateWrapper(entityClass)
                .set("del_flag", param.getNextStatus() == null ? 1 : param.getNextStatus())
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
        return updateByQuery(clazz, EntityUpdate.create()
                .set(properties.getLogicDeleteField(), properties.getLogicDeleteValue())
                .where(meta.getIdArgWhereSql())
                .values(ids));
    }

    @Override
    public TranslateManager getTranslateManager() {
        return getSqlToyContext().getTranslateManager();
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
}
