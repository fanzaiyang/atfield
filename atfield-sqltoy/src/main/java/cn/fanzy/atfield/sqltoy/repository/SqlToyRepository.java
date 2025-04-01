package cn.fanzy.atfield.sqltoy.repository;

import cn.fanzy.atfield.sqltoy.entity.ParamBatchDto;
import cn.fanzy.atfield.sqltoy.mp.IPage;
import com.sagframe.sagacity.sqltoy.plus.dao.SqlToyHelperDao;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.translate.TranslateManager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基本存储库
 *
 * @author fanzaiyang
 * @date 2024-07-01
 */

public interface SqlToyRepository extends SqlToyHelperDao {
    /**
     * 查找一个
     *
     * @param sql   SQL
     * @param param 参数
     * @param clazz 克拉兹
     * @return {@link T }
     */
    <T> T findOne(String sql, Map<String, Object> param, Class<T> clazz);

    /**
     * 查找一个
     *
     * @param sql            SQL
     * @param param          参数
     * @param clazz          克拉兹
     * @param multiException 多异常
     * @return {@link T }
     */
    <T> T findOne(String sql, Map<String, Object> param, Class<T> clazz, boolean multiException);

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
     * @param rootList 最上级的节点集合
     * @return boolean
     */
    <T> boolean wrapTreeTableRoute(final List<T> rootList);

    /**
     * 包装树表路由字段名称
     * 把fieldName字段包装成路由形式后，赋值给targetFieldName字段
     *
     * @param entityClass     实体类
     * @param fieldName       字段名称
     * @param targetFieldName 目标字段名称
     * @return boolean
     */
    <T> boolean wrapTreeTableRouteName(Class<T> entityClass, String fieldName, String targetFieldName);

    /**
     * 转换为mp的IPage
     *
     * @param sourcePage 源页面
     * @return {@link IPage }<{@link T }>
     */
    <T extends Serializable> IPage<T> convert(Page<T> sourcePage);

    /**
     * 逻辑删除
     * delFlag字段
     *
     * @param clazz 克拉兹
     * @param ids   主键
     * @return {@link Long}
     */
    <T> Long remove(Class<T> clazz, Object... ids);

    /**
     * 删除
     *
     * @param clazz 克拉兹
     * @param ids   IDS
     * @return {@link Long }
     */
    <T> Long remove(Class<T> clazz, List<String> ids);

    /**
     * 获取翻译管理器
     *
     * @return {@link TranslateManager }
     */
    TranslateManager getTranslateManager();

    /**
     * 添加缓存
     *
     * @param cacheName 缓存名称
     * @param sql       SQL
     */
    void addCache(String cacheName, String sql);

    /**
     * 添加缓存
     *
     * @param cacheName   缓存名称
     * @param sql         SQL
     * @param forceUpdate 强制更新
     */
    void addCache(String cacheName, String sql, boolean forceUpdate);

    /**
     * 添加缓存检查器
     *
     * @param cacheName      缓存名称
     * @param sql            SQL
     * @param increment      增加
     * @param checkFrequency 检查频率
     */
    void addCacheChecker(String cacheName, String sql, Boolean increment, Integer checkFrequency);

    /**
     * 获取逻辑删除已删除的值
     *
     * @return {@link String }
     */
    <T> String getLogicDeletedValue(Class<T> clazz);
}
