package cn.fanzy.breeze.sqltoy.plus.dao;

import cn.fanzy.breeze.sqltoy.plus.conditions.Wrapper;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.dao.impl.SqlToyLazyDaoImpl;
import org.sagacity.sqltoy.exception.DataAccessException;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.EntityUpdate;
import org.sagacity.sqltoy.model.Page;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings({ "rawtypes" })
@Repository("sqlToyHelperDao")
public class SqlToyHelperDaoImpl extends SqlToyLazyDaoImpl implements SqlToyHelperDao{

    /**
     * 查询一条记录,如果出现多条记录,获取其中第一条记录
     *
     * @param wrapper
     * @return
     */
    public <T> T findOne(Wrapper<T> wrapper) {
        List<T> rs = findList(wrapper);
        if (rs == null || rs.isEmpty()) {
            return null;
        }
        return rs.get(0);
    }

    /**
     * 根据查询条件查询, 仅支持单表条件
     *
     * @param wrapper
     *            - 查询条件
     * @return 结果集
     */
    public <T> List<T> findList(Wrapper<T> wrapper) {
        return super.findEntity(wrapper.entityClass(), getEntityQuery(wrapper));
    }

    /**
     * 根据查询条件分页查询, 仅支持单表条件
     *
     * @param wrapper
     *            - 查询条件
     * @param page
     *            - 分页条件
     * @param <T>
     * @return
     */
    public <T> Page<T> findPage(Wrapper<T> wrapper, Page<?> page) {
        return super.findPageEntity(page, wrapper.entityClass(), getEntityQuery(wrapper));
    }

    /**
     * 删除
     * @param wrapper
     *          - 删除条件
     * @param <T>
     */
    public <T> long delete(Wrapper<T> wrapper) {
        return super.deleteByQuery(wrapper.entityClass(), getEntityQuery(wrapper));
    }

    /**
     * 统计
     * @param wrapper
     *          - 统计条件
     * @param <T>
     */
    public <T> long count(Wrapper<T> wrapper) {
        return super.getCount(wrapper.entityClass(), getEntityQuery(wrapper));
    }

    /**
     * 更新
     * @param updateWrapper
     *          - 更新参数条件对象
     * @param <T>
     */
    public <T> long update(Wrapper<T> updateWrapper) {
        return super.updateByQuery(updateWrapper.entityClass(), getEntityUpdate(updateWrapper));
    }

    /**
     * 更新
     * @param t
     *          - 更新元数据
     * @param wrapper
     *          - 更新参数条件对象
     * @param <T>
     * @return
     */
    public <T> long update(T t, Wrapper<T> wrapper) {
        return super.updateByQuery(wrapper.entityClass(), getEntityUpdate(t, wrapper));
    }

    /**
     *
     * @param setMap
     *          - 更新元数据
     * @param wrapper
     *          - 更新参数条件对象
     * @param <T>
     * @return
     */
    public <T> long update(Map<String, Object> setMap, Wrapper<T> wrapper) {
        return super.updateByQuery(wrapper.entityClass(), getEntityUpdate(setMap, wrapper));
    }

    /**
     * 获取查询条件对象
     * @param wrapper
     *          - 查询条件
     * @param <T>
     * @return
     */
    private <T> EntityQuery getEntityQuery(Wrapper<T> wrapper) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        //开始组装sql
        wrapper.assemble(entityMeta::getColumnName);
        EntityQuery entityQuery = EntityQuery.create().where(wrapper.getSqlSegment()).values(wrapper.getSqlSegmentParamMap());
        if (wrapper.getSelectColumns() != null && !wrapper.getSelectColumns().isEmpty()) {
            entityQuery.select(wrapper.getSelectColumns().toArray(new String[0]));
        }
        return entityQuery;
    }

    /**
     * 组装更新条件
     * @param updateWrapper
     *          - 更新条件
     * @param <T>
     * @return
     */
    private <T> EntityUpdate getEntityUpdate(Wrapper<T> updateWrapper) {
        Map<String, Object> setMap = updateWrapper.getSetMap();
        return getEntityUpdate(setMap, updateWrapper);
    }

    /**
     *
     * @param t
     *          - 更新元数据
     * @param queryWrapper
     *          - 更新的查询条件
     * @param forceUpdateProps
     *          - 忽略字段
     * @param <T>
     * @return
     */
    private <T> EntityUpdate getEntityUpdate(T t, Wrapper<T> queryWrapper, String... forceUpdateProps) {
        EntityUpdate entityUpdate = EntityUpdate.create();
        EntityMeta entityMeta = super.getEntityMeta(queryWrapper.entityClass());
        Iterator<Map.Entry<String, String>> iterator = entityMeta.getColumnFieldMap().entrySet().iterator();
        Map<String, Object> setParamMap = objectToObjectMap(t, forceUpdateProps);
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Object value = setParamMap.get(entry.getKey());
            if (value != null) {
                entityUpdate.set(entry.getKey(), value);
            }
        }
        return getEntityUpdate(setParamMap, queryWrapper);
    }

    /**
     * 组装更新条件
     * @param queryWrapper
     *          - 更新的查询条件
     * @param <T>
     * @return
     */
    private <T> EntityUpdate getEntityUpdate(Map<String, Object> setMap, Wrapper<T> queryWrapper) {
        if (setMap == null || setMap.size() <= 0) {
            throw new DataAccessException("sqlToy plus update param can not empty");
        }
        EntityMeta entityMeta = super.getEntityMeta(queryWrapper.entityClass());
        //开始组装sql
        queryWrapper.assemble(entityMeta::getColumnName);
        EntityUpdate entityUpdate = EntityUpdate.create();
        setMap.forEach(entityUpdate::set);
        return entityUpdate.where(queryWrapper.getSqlSegment()).values(queryWrapper.getSqlSegmentParamMap());
    }

    /**
     * 转换对象为map
     *
     * @param object
     * @param ignore
     * @return HashMap<String, Object>
     */
    public static HashMap<String, Object> objectToObjectMap(Object object, String... ignore) {
        if (object == null) {
            return null;
        }
        HashMap<String, Object> tempMap = new LinkedHashMap<String, Object>();
        for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        // 排除类变量
                        if (Modifier.isStatic(field.getModifiers())) {
                            continue;
                        }
                        boolean ig = false;
                        if (ignore != null && ignore.length > 0) {
                            for (String i : ignore) {
                                if (i.equals(field.getName())) {
                                    ig = true;
                                    break;
                                }
                            }
                        }
                        if (ig) {
                            continue;
                        }
                        Object o = null;
                        try {
                            o = field.get(object);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(o != null) {
                            tempMap.put(field.getName(), o);
                        }
                    }
                }
            } catch (Exception e) {
                throw new DataAccessException("sqlToy plus entity to map occur exception");
            }
        }
        return tempMap;
    }
}
