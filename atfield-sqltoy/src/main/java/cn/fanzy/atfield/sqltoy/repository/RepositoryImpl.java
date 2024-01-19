package cn.fanzy.atfield.sqltoy.repository;

import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.dao.impl.LightDaoImpl;
import org.sagacity.sqltoy.model.TreeTableModel;

import java.io.Serializable;

/**
 * 基本存储库 impl
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@RequiredArgsConstructor
public class RepositoryImpl extends LightDaoImpl implements Repository {
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
