package cn.fanzy.atfield.sqltoy.repository;

import cn.fanzy.atfield.sqltoy.entity.ParamBatchDto;
import cn.fanzy.atfield.sqltoy.mp.IPage;
import com.sagframe.sagacity.sqltoy.plus.dao.SqlToyHelperDao;
import org.sagacity.sqltoy.model.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 基本存储库
 *
 * @author fanzaiyang
 * @date 2024-07-01
 */

public interface SqlToyRepository extends SqlToyHelperDao {

    /**
     * 处理更新状态
     *
     * @param entityClass Entity 类
     * @param param       参数
     */
    void handleUpdateStatus(Class<?> entityClass, ParamBatchDto param);

    /**
     * 处理更新树状态
     *
     * @param entityClass 实体类
     * @param param       参数
     */
    void handleUpdateTreeStatus(Class<?> entityClass, ParamBatchDto param);
    /**
     * 处理更新删除
     *
     * @param entityClass Entity 类
     * @param param       参数
     */
    void handleUpdateDelete(Class<?> entityClass, ParamBatchDto param);

    /**
     * 处理更新树删除
     *
     * @param entityClass 实体类
     * @param param       参数
     */
    void handleUpdateTreeDelete(Class<?> entityClass, ParamBatchDto param);
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
     * 换行树表路由
     *
     * @param entities 实体
     * @return boolean
     */
    boolean wrapTreeTableRoute(final List<Serializable> entities);

    /**
     * 转换为mp的IPage
     *
     * @param sourcePage 源页面
     * @return {@link IPage }<{@link T }>
     */
    <T extends Serializable> IPage<T> convert(Page<T> sourcePage);
}
