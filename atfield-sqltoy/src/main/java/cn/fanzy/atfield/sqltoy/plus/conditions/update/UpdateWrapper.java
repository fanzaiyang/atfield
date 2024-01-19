package cn.fanzy.atfield.sqltoy.plus.conditions.update;

import cn.fanzy.atfield.sqltoy.plus.conditions.AbstractUpdateWrapper;
import cn.fanzy.atfield.sqltoy.plus.conditions.segments.MergeSegments;
import cn.fanzy.atfield.sqltoy.plus.conditions.toolkit.SqlStringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateWrapper<T> extends AbstractUpdateWrapper<T, String, UpdateWrapper<T>> implements Update<UpdateWrapper<T>, String>{

    private final Map<String, Object> setMap = new HashMap<>();

    public UpdateWrapper(Class<T> entityClass) {
        this(new MergeSegments(), entityClass);
    }

    protected UpdateWrapper(MergeSegments expression, Class<T> entityClass) {
        super(expression, entityClass);
    }

    protected UpdateWrapper(MergeSegments expression, AtomicInteger paramNameSeq) {
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
    protected UpdateWrapper<T> instance() {
        return new UpdateWrapper<>(new MergeSegments(), paramNameSeq);
    }
}
