package cn.fanzy.atfield.sqltoy.plus.conditions;

import java.util.List;
import java.util.Map;


public interface Wrapper<T> extends ISqlAssembler, ISqlSegment {

    /**
     * 实体类
     *
     * @return {@link Class}<{@link T}>
     */
    Class<T> entityClass();

    /**
     * 得到集地图
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    default Map<String, Object> getSetMap() {
        return null;
    }

    /**
     * 得到选择列
     *
     * @return {@link List}<{@link String}>
     */
    default List<String> getSelectColumns() {
        return null;
    }
}
