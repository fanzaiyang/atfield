package cn.fanzy.atfield.sqltoy.plus.conditions.query;

import java.io.Serializable;

/**
 * 查询
 *
 * @author fanzaiyang
 * @date 2023-06-15
 */
public interface Query<Children, R> extends Serializable {

    /**
     * 选择
     *
     * @param columns 列
     * @return {@link Children}
     */
    @SuppressWarnings("unchecked")
    Children select(R... columns);
}
