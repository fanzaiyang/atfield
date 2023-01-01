package cn.fanzy.breeze.sqltoy.plus.dao;

import cn.fanzy.breeze.sqltoy.plus.conditions.Wrapper;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.Page;

import java.util.List;
import java.util.Map;

/**
 * sqltoy-plus扩展接口
 */
public interface SqlToyHelperDao extends SqlToyLazyDao {

    /**
     * 查询一条记录,如果出现多条记录,获取其中第一条记录
     * @param <T> T
     * @param wrapper Wrapper
     * @return T
     */
    <T> T findOne(Wrapper<T> wrapper);

    /**
     * 根据查询条件查询, 仅支持单表条件
     * @param <T> T
     * @param wrapper - 查询条件
     * @return 结果集
     */
    <T> List<T> findList(Wrapper<T> wrapper);

    /**
     * 根据查询条件分页查询, 仅支持单表条件
     *
     * @param wrapper - 查询条件
     * @param page    - 分页条件
     * @param <T> T
     * @return Page
     */
    <T> Page<T> findPage(Wrapper<T> wrapper, Page<?> page);

    /**
     * 删除
     *
     * @param wrapper - 删除条件
     * @param <T> T
     * @return long
     */
    <T> long delete(Wrapper<T> wrapper);

    /**
     * 统计
     *
     * @param wrapper - 统计条件
     * @param <T> T
     * @return long
     */
    <T> long count(Wrapper<T> wrapper);

    /**
     * 是否存在某个条件数据
     *
     * @param wrapper - 统计条件
     * @param <T> T
     * @return boolen
     */
    default <T> boolean exists(Wrapper<T> wrapper) {
        return count(wrapper) > 0;
    }

    /**
     * 更新
     *
     * @param updateWrapper - 更新参数条件对象
     * @param <T> T
     * @return long
     */
    <T> long update(Wrapper<T> updateWrapper);

    /**
     * 更新
     *
     * @param t       - 更新元数据
     * @param wrapper - 更新参数条件对象
     * @param <T> T
     * @return long
     */
    <T> long update(T t, Wrapper<T> wrapper);

    /**
     * @param setMap  - 更新元数据
     * @param wrapper - 更新参数条件对象
     * @param <T> T
     * @return long
     */
    <T> long update(Map<String, Object> setMap, Wrapper<T> wrapper);
}
