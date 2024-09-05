package cn.fanzy.atfield.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * tree 节点
 *
 * @author fanzaiyang
 * @date 2024/08/28
 */
public interface TreeNode<E> extends Serializable {


    /**
     * 节点ID
     *
     * @return {@link String }
     */
    String getId();

    /**
     * 父节点
     *
     * @return {@link String }
     */
    String getParentId();

    /**
     * 是叶子节点
     *
     * @return boolean
     */
    boolean isLeaf();

    /**
     * 获取字节点列表
     *
     * @return {@link List }<{@link E }>
     */
    List<E> getChildren();

    /**
     * 设置字节点列表
     *
     * @param children 子节点列表
     */
    void setChildren(List<E> children);
}
