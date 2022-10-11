package cn.fanzy.breeze.sqltoy.plus.conditions;


import cn.fanzy.breeze.sqltoy.plus.conditions.query.LambdaQueryWrapper;
import cn.fanzy.breeze.sqltoy.plus.conditions.query.QueryWrapper;
import cn.fanzy.breeze.sqltoy.plus.conditions.update.LambdaUpdateWrapper;
import cn.fanzy.breeze.sqltoy.plus.conditions.update.UpdateWrapper;

/**
 * 条件对象获取
 */
public final class Wrappers<T> {

    public static <T> QueryWrapper<T> wrapper(Class<T> entityClass) {
        return new QueryWrapper<T>(entityClass);
    }

    public static <T> LambdaQueryWrapper<T> lambdaWrapper(Class<T> entityClass) {
        return new LambdaQueryWrapper<T>(entityClass);
    }

    public static <T> UpdateWrapper<T> updateWrapper(Class<T> entityClass) {
        return new UpdateWrapper<T>(entityClass);
    }

    public static <T> LambdaUpdateWrapper<T> lambdaUpdateWrapper(Class<T> entityClass) {
        return new LambdaUpdateWrapper<T>(entityClass);
    }
}
