package cn.fanzy.atfield.core.utils.tree.model;

import java.io.Serializable;
import java.util.List;

public interface ITreeNode<T> extends Serializable {

    /**
     * 获取 ID
     *
     * @return {@link String }
     */
    String getId();

    /**
     * 获取父 ID
     *
     * @return {@link String }
     */
    String getParentId();

    /**
     * 获取子项
     *
     * @return {@link List }<{@link T }>
     */
    List<T> getChildren();

    /**
     * 设置子项
     *
     * @param children 孩子
     */
    void setChildren(List<T> children);

    /**
     * 是否是叶子节点
     *
     * @return boolean
     */
    boolean isLeaf();

    /**
     * 是否有子项
     *
     * @return boolean
     */
    default boolean hasChildren() {
        return !isLeaf();
    }
}
