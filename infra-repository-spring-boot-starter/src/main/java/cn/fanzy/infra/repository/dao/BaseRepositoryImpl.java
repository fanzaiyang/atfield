package cn.fanzy.infra.repository.dao;

import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.dao.impl.LightDaoImpl;
import org.sagacity.sqltoy.model.TreeTableModel;

import java.io.Serializable;

@RequiredArgsConstructor
public class BaseRepositoryImpl extends LightDaoImpl implements BaseRepository {
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
}
