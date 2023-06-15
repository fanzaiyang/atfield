package cn.fanzy.breeze.sqltoy.plus.dao;

import cn.fanzy.breeze.sqltoy.enums.LogicalDeleteEnum;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrapper;
import cn.fanzy.breeze.sqltoy.plus.utils.PlusDaoUtil;
import cn.fanzy.breeze.sqltoy.properties.BreezeSqlToyProperties;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.dao.impl.SqlToyLazyDaoImpl;
import org.sagacity.sqltoy.exception.DataAccessException;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.EntityUpdate;
import org.sagacity.sqltoy.model.Page;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * sqltoy
 *
 * @author fanzaiyang
 */
@Slf4j
public class SqlToyHelperDaoImpl extends SqlToyLazyDaoImpl implements SqlToyHelperDao {


    public <T> T findOne(Wrapper<T> wrapper) {
        List<T> rs = findList(wrapper);
        if (rs == null || rs.isEmpty()) {
            return null;
        }
        return rs.get(0);
    }


    public <T> List<T> findList(Wrapper<T> wrapper) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityQuery entityQuery = PlusDaoUtil.getEntityQuery(wrapper, entityMeta);
        return super.findEntity(wrapper.entityClass(), entityQuery);
    }


    public <T> Page<T> findPage(Wrapper<T> wrapper, Page<?> page) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityQuery entityQuery = PlusDaoUtil.getEntityQuery(wrapper, entityMeta);
        return super.findPageEntity(page, wrapper.entityClass(), entityQuery);
    }


    public <T> long delete(Wrapper<T> wrapper) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityQuery entityQuery = PlusDaoUtil.getEntityQuery(wrapper, entityMeta);
        return super.deleteByQuery(wrapper.entityClass(), entityQuery);
    }


    public <T> long count(Wrapper<T> wrapper) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityQuery entityQuery = PlusDaoUtil.getEntityQuery(wrapper, entityMeta);
        return super.getCount(wrapper.entityClass(), entityQuery);
    }


    public <T> long update(Wrapper<T> updateWrapper) {
        EntityMeta entityMeta = super.getEntityMeta(updateWrapper.entityClass());
        EntityUpdate entityUpdate = PlusDaoUtil.getEntityUpdate(updateWrapper, entityMeta);
        return super.updateByQuery(updateWrapper.entityClass(), entityUpdate);
    }


    public <T> long update(T t, Wrapper<T> wrapper) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityUpdate entityUpdate = PlusDaoUtil.getEntityUpdate(t,wrapper, entityMeta);
        return super.updateByQuery(wrapper.entityClass(), entityUpdate);
    }


    public <T> long update(Map<String, Object> setMap, Wrapper<T> wrapper) {
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityUpdate entityUpdate = PlusDaoUtil.getEntityUpdate(setMap,wrapper, entityMeta);
        return super.updateByQuery(wrapper.entityClass(),entityUpdate);
    }

    @Override
    public <T> Long remove(Wrapper<T> wrapper) {
        BreezeSqlToyProperties properties = SpringUtil.getBean(BreezeSqlToyProperties.class);
        Map<String, Object> setMap = new HashMap<>(1);
        setMap.put(properties.getLogicDeleteField(), PlusDaoUtil.getDeleteValue(properties));
        EntityMeta entityMeta = super.getEntityMeta(wrapper.entityClass());
        EntityUpdate entityUpdate = PlusDaoUtil.getEntityUpdate(setMap,wrapper, entityMeta);
        return super.updateByQuery(wrapper.entityClass(), entityUpdate);
    }
}
