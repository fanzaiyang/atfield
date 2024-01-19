package cn.fanzy.atfield.sqltoy.plus.conditions.interfaces;


public interface Operated<Children extends Operated<Children, R>, R> extends Compare<Children, R>, Nested<Children, Children>, Join<Children>, Func<Children, R>{
}
