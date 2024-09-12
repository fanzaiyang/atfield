package cn.fanzy.atfield.sqltoy.repository.impl;

import cn.fanzy.atfield.sqltoy.entity.ParamBatchDto;
import cn.fanzy.atfield.sqltoy.mp.IPage;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import com.sagframe.sagacity.sqltoy.plus.conditions.Wrappers;
import com.sagframe.sagacity.sqltoy.plus.dao.SqlToyHelperDaoImpl;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.model.MapKit;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.model.TreeTableModel;

import java.io.Serializable;
import java.util.List;

/**
 * 基础存储 库实现类
 *
 * @author fanzaiyang
 * @date 2024-07-01
 */
public class SqlToyRepositoryImpl extends SqlToyHelperDaoImpl implements SqlToyRepository {
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
}
