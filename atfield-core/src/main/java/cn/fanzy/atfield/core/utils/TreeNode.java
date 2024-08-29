package cn.fanzy.atfield.core.utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author fanzaiyang
 * @date 2024/08/28
 */
public abstract class TreeNode<E> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2254859224968094447L;

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
     * @return {@link List }<{@link E }>
     */
    public abstract List<E> getChildren();

    /**
     * 设置字节点列表
     *
     * @param children 子节点列表
     */
    public abstract void setChildren(List<E> children);
}
