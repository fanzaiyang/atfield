package cn.fanzy.atfield.sqltoy.flex.query;

/**
 * 包装
 *
 * @author fanzaiyang
 * @date 2024/01/18
 */
public class Wrappers {

    public static <T> LambdaEntityQuery<T> lambdaQuery(Class<T> clazz) {
        return new LambdaEntityQuery<>(clazz);
    }
}
