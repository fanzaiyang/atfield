package cn.fanzy.breeze.sqltoy.plus.conditions.update;



import cn.fanzy.breeze.sqltoy.plus.conditions.AbstractUpdateWrapper;
import cn.fanzy.breeze.sqltoy.plus.conditions.interfaces.SFunction;
import cn.fanzy.breeze.sqltoy.plus.conditions.segments.MergeSegments;
import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.LambdaUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class LambdaUpdateWrapper<T> extends AbstractUpdateWrapper<T, SFunction<T, ?>, LambdaUpdateWrapper<T>> {

    public LambdaUpdateWrapper(Class<T> entityClass) {
        this(new MergeSegments(), entityClass);
    }

    protected LambdaUpdateWrapper(MergeSegments expression, Class<T> entityClass) {
        super(expression, entityClass);
    }

    protected LambdaUpdateWrapper(MergeSegments expression, AtomicInteger paramNameSeq) {
        super(expression, paramNameSeq);
    }

    @Override
    protected LambdaUpdateWrapper<T> instance() {
        return new LambdaUpdateWrapper<>(new MergeSegments(), paramNameSeq);
    }

    @Override
    protected String columnToString(SFunction<T, ?> column) {
        return LambdaUtils.extractToFiled(column);
    }
}
