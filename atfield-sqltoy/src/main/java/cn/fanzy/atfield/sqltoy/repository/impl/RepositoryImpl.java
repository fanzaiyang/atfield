package cn.fanzy.atfield.sqltoy.repository.impl;

import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.fanzy.atfield.sqltoy.repository.Repository;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.dao.impl.LightDaoImpl;
import org.sagacity.sqltoy.model.EntityUpdate;
import org.sagacity.sqltoy.model.TreeTableModel;

import java.io.Serializable;
import java.util.List;

/**
 * 基本存储库 impl
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@RequiredArgsConstructor
public class RepositoryImpl extends LightDaoImpl implements Repository {
    private final SqltoyExtraProperties properties;

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
    public boolean wrapTreeTableRoute(List<Serializable> entities, String pidField) {
        for (Serializable entity : entities) {
            wrapTreeTableRoute(entity, pidField);
        }
        return true;
    }

    @Override
    public <T> Long remove(Class<T> clazz, Object... ids) {
        EntityMeta meta = getEntityMeta(clazz);
        return updateByQuery(clazz, EntityUpdate.create()
                .set(properties.getLogicDeleteField(), properties.getLogicDeleteValue())
                .where(meta.getIdArgWhereSql())
                .values(ids));
    }
}
