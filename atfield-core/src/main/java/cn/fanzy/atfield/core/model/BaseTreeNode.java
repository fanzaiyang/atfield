package cn.fanzy.atfield.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 基础树节点
 *
 * @author fanzaiyang
 * @date 2024/08/28
 */
@Getter
@Setter
public abstract class BaseTreeNode<E> implements Serializable {

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

}
