package cn.fanzy.breeze.sqltoy.plus.utils;

import cn.fanzy.breeze.sqltoy.enums.LogicalDeleteEnum;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrapper;
import cn.fanzy.breeze.sqltoy.properties.BreezeSqlToyProperties;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.exception.DataAccessException;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.EntityUpdate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * +刀工具类
 *
 * @author fanzaiyang
 * @date 2023-06-14
 */
@Slf4j
public class PlusDaoUtil {
    /**
     * 得到实体查询
     *
     * @param wrapper    包装器
     * @param entityMeta 实体元
     * @return {@link EntityQuery}
     */
    public static <T> EntityQuery getEntityQuery(Wrapper<T> wrapper,EntityMeta entityMeta) {
        //开始组装sql
        wrapper.assemble(entityMeta::getColumnName);
        EntityQuery entityQuery = EntityQuery.create().where(wrapper.getSqlSegment());
        // fix where无参数，报org.sagacity.sqltoy.utils.SqlUtil：Parameter index out of range (1 > number of parameters, which is 0).问题
        Map<String, Object> paramMap = wrapper.getSqlSegmentParamMap();
        if (paramMap != null && !paramMap.isEmpty()) {
            entityQuery.values(paramMap);
        }

        if (wrapper.getSelectColumns() != null && !wrapper.getSelectColumns().isEmpty()) {
            entityQuery.select(wrapper.getSelectColumns().toArray(new String[0]));
        }
        return entityQuery;
    }

    /**
     * 得到实体更新
     * 组装更新条件
     *
     * @param updateWrapper - 更新条件
     * @return EntityUpdate
     */
    public static <T> EntityUpdate getEntityUpdate(Wrapper<T> updateWrapper,EntityMeta entityMeta) {
        Map<String, Object> setMap = updateWrapper.getSetMap();
        return getEntityUpdate(setMap, updateWrapper,entityMeta);
    }

    /**
     * 得到实体更新
     *
     * @param t                - 更新元数据
     * @param queryWrapper     - 更新的查询条件
     * @param forceUpdateProps - 忽略字段
     * @param entityMeta       实体元
     * @return EntityUpdate
     */
    public static <T> EntityUpdate getEntityUpdate(T t, Wrapper<T> queryWrapper,EntityMeta entityMeta, String... forceUpdateProps) {
        EntityUpdate entityUpdate = EntityUpdate.create();
        Iterator<Map.Entry<String, String>> iterator = entityMeta.getColumnFieldMap().entrySet().iterator();
        Map<String, Object> setParamMap = objectToObjectMap(t, forceUpdateProps);
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            Object value = setParamMap.get(entry.getKey());
            if (value != null) {
                entityUpdate.set(entry.getKey(), value);
            }
        }
        return getEntityUpdate(setParamMap, queryWrapper,entityMeta);
    }

    /**
     * 得到实体更新
     * 组装更新条件
     *
     * @param queryWrapper - 更新的查询条件
     * @param setMap       设置地图
     * @param entityMeta   实体元
     * @return EntityUpdate
     */
    public static <T> EntityUpdate getEntityUpdate(Map<String, Object> setMap, Wrapper<T> queryWrapper,EntityMeta entityMeta) {
        if (setMap == null || setMap.size() == 0) {
            throw new DataAccessException("sqlToy plus update param can not empty");
        }
        //开始组装sql
        queryWrapper.assemble(entityMeta::getColumnName);
        EntityUpdate entityUpdate = EntityUpdate.create();
        setMap.forEach(entityUpdate::set);
        String sqlSegment = queryWrapper.getSqlSegment();
        Map<String, Object> paramMap = queryWrapper.getSqlSegmentParamMap();
        // 当where无参数时，添加1=1，解决IllegalArgumentException异常
        if (paramMap.isEmpty()) {
            sqlSegment = StrUtil.isBlank(sqlSegment) ? "1=:DEFAULT_VALUE " : ("1=:DEFAULT_VALUE AND " + sqlSegment);
            paramMap.put("DEFAULT_VALUE", 1);
        }
        return entityUpdate.where(sqlSegment).values(paramMap);
    }

    /**
     * 转换对象为map
     *
     * @param object Object
     * @param ignore String[]
     * @return HashMap
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
                            log.error(e.getMessage(), e);
                        }
                        if (o != null) {
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
    public static String getDeleteValue(BreezeSqlToyProperties properties) {
        if (LogicalDeleteEnum.value.equals(properties.getDeleteValueStrategy())) {
            Assert.notBlank(properties.getLogicDeleteField(), "请在配置文件中配置逻辑删除字段！");
            Assert.notBlank(properties.getLogicDeleteValue(), "请在配置文件中配置逻辑删除字段值logicDeleteValue！");
            return properties.getLogicDeleteValue();
        }
        if (LogicalDeleteEnum.uuid.equals(properties.getDeleteValueStrategy())) {
            return IdUtil.randomUUID();
        }
        if (LogicalDeleteEnum.nanoTime.equals(properties.getDeleteValueStrategy())) {
            return IdUtil.nanoId();
        }
        if (LogicalDeleteEnum.snowflake.equals(properties.getDeleteValueStrategy())) {
            return IdUtil.getSnowflakeNextId() + "";
        }
        throw new RuntimeException("未知的删除值生成逻辑！");
    }

    public static Object convertVal(Object val){
        return ObjectUtil.isNull(val) ? "null" : val;
    }
}
