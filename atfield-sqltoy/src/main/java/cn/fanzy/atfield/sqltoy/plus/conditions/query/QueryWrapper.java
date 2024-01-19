package cn.fanzy.atfield.sqltoy.plus.conditions.query;

import cn.fanzy.atfield.sqltoy.plus.conditions.AbstractQueryWrapper;
import cn.fanzy.atfield.sqltoy.plus.conditions.segments.MergeSegments;
import cn.fanzy.atfield.sqltoy.plus.conditions.toolkit.SqlStringUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class QueryWrapper<T> extends AbstractQueryWrapper<T, String, QueryWrapper<T>> {

    public QueryWrapper(Class<T> entityClass) {
        this(new MergeSegments(), entityClass);
    }

    protected QueryWrapper(MergeSegments expression, Class<T> entityClass) {
        super(expression, entityClass);
    }

    protected QueryWrapper(MergeSegments expression, AtomicInteger paramNameSeq) {
        super(expression, paramNameSeq);
    }

    @Override
    protected String columnToString(String column) {
        return column;
    }

    @Override
    protected String columnSqlInjectFilter(String column) {
        return SqlStringUtil.sqlInjectionReplaceBlank(column);
    }

    @Override
    protected QueryWrapper<T> instance() {
        return new QueryWrapper<T>(new MergeSegments(), paramNameSeq);
    }
}
