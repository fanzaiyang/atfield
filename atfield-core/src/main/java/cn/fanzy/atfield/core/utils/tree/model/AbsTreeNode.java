package cn.fanzy.atfield.core.utils.tree.model;

import java.io.Serial;
import java.util.List;

/**
 * 基础树节点
 *
 * @author fanzaiyang
 * @date 2024/08/28
 */
public abstract class AbsTreeNode<E> implements ITreeNode<E> {

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
     * 获取子项
     *
     * @return {@link List }<{@link E }>
     */
    @Override
    public List<E> getChildren() {
        return children;
    }

    /**
     * 设置子项
     *
     * @param children 孩子
     */
    @Override
    public void setChildren(List<E> children) {
        this.children = children;
    }

    /**
     * 是叶子节点
     *
     * @return boolean
     */
    public abstract boolean isLeaf();

    /**
     * 是否有下级节点
     *
     * @return boolean
     */
    public boolean isHasChildren() {
        return !isLeaf();
    }

}
