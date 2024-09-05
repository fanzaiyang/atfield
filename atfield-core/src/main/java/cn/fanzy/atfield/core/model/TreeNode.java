package cn.fanzy.atfield.core.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * tree 节点
 *
 * @author fanzaiyang
 * @date 2024/08/28
 */
public abstract class TreeNode<E> implements Serializable {

    @Serial
    private static final long serialVersionUID = -2254859224968094447L;

    /**
     * 下级节点列表
     */
    private List<E> children;

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
     * 是叶子节点
     *
     * @return boolean
     */
    public abstract boolean isLeaf();

    /**
     * 获取字节点列表
     *
     * @return {@link List }<{@link E }>
     */
    public List<E> getChildren() {
        return this.children;
    }

    /**
     * 设置字节点列表
     *
     * @param children 子节点列表
     */
    public void setChildren(List<E> children) {
        this.children = children;
    }
}
