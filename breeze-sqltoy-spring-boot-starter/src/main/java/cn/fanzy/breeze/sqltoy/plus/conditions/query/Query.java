package cn.fanzy.breeze.sqltoy.plus.conditions.query;

import java.io.Serializable;

public interface Query<Children, R> extends Serializable {

    @SuppressWarnings("unchecked")
    Children select(R... columns);
}
