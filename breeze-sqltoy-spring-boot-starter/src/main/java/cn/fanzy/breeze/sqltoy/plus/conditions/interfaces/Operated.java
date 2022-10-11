package cn.fanzy.breeze.sqltoy.plus.conditions.interfaces;

/**
 * sql操作相关接口
 * @param <Children>
 * @param <R>
 */
public interface Operated<Children extends Operated<Children, R>, R> extends Compare<Children, R>, Nested<Children, Children>, Join<Children>, Func<Children, R>{
}
