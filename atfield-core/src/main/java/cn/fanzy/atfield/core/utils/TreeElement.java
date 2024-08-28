package cn.fanzy.atfield.core.utils;

import java.util.List;

/**
 * @author fanzaiyang
 * @date 2024/08/28
 */
public abstract class TreeElement<T> {

    /**
     * 节点ID
     *
     * @return {@link String }
     */
    public abstract String getId();

    /**
     * 父节点
     *
     * @return {@link String }
     */
    public abstract String getParentId();


    /**
     * 获取字节点列表
     *
     * @return {@link List }<{@link T }>
     */
    public abstract List<T> getChildren();

    /**
     * 设置字节点列表
     *
     * @param children 子节点列表
     */
    public abstract void setChildren(List<T> children);
}
