package cn.fanzy.infra.repository.dao;

import org.sagacity.sqltoy.dao.LightDao;

import java.io.Serializable;

public interface BaseRepository extends LightDao {
    /**
     * 换行树表路由
     *
     * @param entity 实体
     * @return boolean
     */
    boolean wrapTreeTableRoute(final Serializable entity);

    /**
     * 换行树表路由
     *
     * @param entity   实体
     * @param pidField PID 字段
     * @return boolean
     */
    boolean wrapTreeTableRoute(final Serializable entity, String pidField);

}
