package cn.fanzy.atfield.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * 基础树节点接口
 *
 * @author fanzaiyang
 * @date 2024/08/28
 */
public interface ITreeNode<E> extends Serializable {


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
     * 获取子项
     *
     * @return {@link List }<{@link E }>
     */
    List<E> getChildren();

    /**
     * 设置子项
     *
     * @param children 孩子
     */
    void setChildren(List<E> children);

}
