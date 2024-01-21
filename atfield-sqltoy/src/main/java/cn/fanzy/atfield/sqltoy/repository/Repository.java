package cn.fanzy.atfield.sqltoy.repository;

import org.sagacity.sqltoy.dao.LightDao;

import java.io.Serializable;

/**
 * 基础存储库
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
public interface Repository extends LightDao {
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

    /**
     * 逻辑删除
     *
     * @param clazz 克拉兹
     * @param ids   主键
     * @return {@link Long}
     */
    <T> Long remove(Class<T> clazz, Object... ids);

}
