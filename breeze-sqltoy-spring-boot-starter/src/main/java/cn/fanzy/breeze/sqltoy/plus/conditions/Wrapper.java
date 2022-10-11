package cn.fanzy.breeze.sqltoy.plus.conditions;

import java.util.List;
import java.util.Map;

/**
 * sql动态组装
 * @param <T>
 */
public interface Wrapper<T> extends ISqlAssembler, ISqlSegment {

    Class<T> entityClass();

    default Map<String, Object> getSetMap() {
        return null;
    }

    default List<String> getSelectColumns() {
        return null;
    }
}
