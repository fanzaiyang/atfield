package cn.fanzy.breeze.sqltoy.plus.conditions;


import cn.fanzy.breeze.sqltoy.plus.conditions.segments.MergeSegments;
import cn.fanzy.breeze.sqltoy.plus.conditions.update.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractUpdateWrapper<T, R, Children extends AbstractUpdateWrapper<T, R, Children>> extends AbstractWrapper<T, R, Children>  implements Update<Children, R> {

    private final Map<String, Object> setMap = new HashMap<>();

    protected AbstractUpdateWrapper(MergeSegments expression, AtomicInteger paramNameSeq) {
        super(expression, paramNameSeq);
    }

    public AbstractUpdateWrapper(MergeSegments expression, Class<T> entityClass) {
        super(expression, entityClass);
    }

    @Override
    public Map<String, Object> getSetMap() {
        return setMap;
    }

    @Override
    public final Children set(boolean condition, R column, Object val) {
        maybeDo(condition, () -> setMap.put(columnsToString(column), val));
        return typedThis;
    }
}
