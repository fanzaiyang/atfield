package cn.fanzy.breeze.sqltoy.plus.conditions.query;


import cn.fanzy.breeze.sqltoy.plus.conditions.AbstractQueryWrapper;
import cn.fanzy.breeze.sqltoy.plus.conditions.interfaces.SFunction;
import cn.fanzy.breeze.sqltoy.plus.conditions.segments.MergeSegments;
import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.LambdaUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class LambdaQueryWrapper<T> extends AbstractQueryWrapper<T, SFunction<T, ?>, LambdaQueryWrapper<T>> {

    public LambdaQueryWrapper(Class<T> entityClass) {
        this(new MergeSegments(), entityClass);
    }

    protected LambdaQueryWrapper(MergeSegments expression, Class<T> entityClass) {
        super(expression, entityClass);
    }

    protected LambdaQueryWrapper(MergeSegments expression, AtomicInteger paramNameSeq) {
        super(expression, paramNameSeq);
    }

    @Override
    protected LambdaQueryWrapper<T> instance() {
        return new LambdaQueryWrapper<T>(new MergeSegments(), paramNameSeq);
    }

    @Override
    protected String columnToString(SFunction<T, ?> column) {
        return LambdaUtils.extractToFiled(column);
    }
}
