package cn.fanzy.breeze.sqltoy.plus.conditions;


import cn.fanzy.breeze.sqltoy.plus.conditions.query.Query;
import cn.fanzy.breeze.sqltoy.plus.conditions.segments.MergeSegments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 抽象查询包装
 *
 * @author fanzaiyang
 * @date 2023-06-15
 */
public abstract class AbstractQueryWrapper<T, R, Children extends AbstractQueryWrapper<T, R, Children>> extends AbstractWrapper<T, R, Children> implements Query<Children, R> {

    private final List<String> selectColumns = new ArrayList<>();

    private final Map<String, Boolean> skipMap = new HashMap<>();

    protected AbstractQueryWrapper(MergeSegments expression, AtomicInteger paramNameSeq) {
        super(expression, paramNameSeq);
    }

    public AbstractQueryWrapper(MergeSegments expression, Class<T> entityClass) {
        super(expression, entityClass);
    }

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    @SafeVarargs
    @Override
    public final Children select(R... columns) {
        for (R r : columns) {
            selectColumns.add(columnToString(r));
        }
        return typedThis;
    }
}
