package cn.fanzy.atfield.core.utils.tree;

import cn.fanzy.atfield.core.model.BaseTreeNode;
import cn.fanzy.atfield.core.utils.tree.model.ITreeNode;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tree工具类
 * <p>
 * 1. 提供高效的list转tree方法。时间复杂度：O(3n)或O(n)，5万的数据约25ms。
 * </p>
 *
 * @author fanzaiyang
 * @date 2025/04/27
 */
public class TreeUtils {
    /**
     * 根父 ID
     */
    public static final String ROOT_PARENT_ID = "-1";

    /**
     * 获取根节点
     * <p>检查pid是否存在于id集合中</p>
     *
     * @param listData           数据
     * @param isChangeOriginData 是否更改源数据；true：会修改参数listData,此方式效率也是最高。
     * @return {@link List }<{@link E }> 新的集合
     */
    public static <E extends BaseTreeNode<E>> List<E> getRootNodeList(List<E> listData, boolean isChangeOriginData) {
        return getRootNodeList(listData, BaseTreeNode::getId, BaseTreeNode::getParentId, isChangeOriginData);
    }

    /**
     * 获取根节点列表
     * <p>检查pid是否存在于id集合中</p>
     *
     * @param listData           列出数据
     * @param idFunc             id func
     * @param pidFunc            pid func
     * @param isChangeOriginData 是更改源数据
     * @return {@link List }<{@link E }>
     */
    public static <K, E> List<E> getRootNodeList(List<E> listData, Function<E, K> idFunc, Function<E, K> pidFunc, boolean isChangeOriginData) {
        // 检查pid是否存在于id集合中
        Set<K> allIdList = listData.stream().map(idFunc).collect(Collectors.toSet());
        // 更改源数据，效率更改
        if (isChangeOriginData) {
            listData.removeIf(data -> allIdList.contains(pidFunc.apply(data)));
            return listData;
        }
        return listData.stream().filter(data -> !allIdList.contains(pidFunc.apply(data))).toList();
    }

    /**
     * 生成树结构
     * <pre>
     * 1. 要求传入的类中包含id、parentId、children三个字段
     * 2. 时间复杂度：O(3n)或O(n)，5万的数据约25ms
     * </pre>
     *
     * @param listData 列出数据
     */
    public static <E extends BaseTreeNode<E>> void makeTree(List<E> listData) {
        makeTree(listData, null);
    }

    /**
     * 生成树结构
     * <pre>
     * 1. 要求传入的类中包含id、parentId、children三个字段
     * 2. 时间复杂度：O(3n)或O(n)，5万的数据约25ms
     * </pre>
     *
     * @param listData 列出数据
     * @param rootId   指定根ID，如果为空，则自动查询根节点
     */
    public static <E extends BaseTreeNode<E>> void makeTree(List<E> listData, String rootId) {
        if (StrUtil.isBlank(rootId)) {
            makeTree(listData, BaseTreeNode::getId, BaseTreeNode::getParentId, BaseTreeNode::setChildren, null);
            return;
        }
        makeTree(listData, BaseTreeNode::getId, BaseTreeNode::getParentId, BaseTreeNode::setChildren, x -> rootId.equals(x.getId()));
    }

    /**
     * 制作iTree
     *
     * @param listData 列出数据
     */
    public static <E extends ITreeNode<E>> void makeITree(List<E> listData) {
        makeITree(listData, null);
    }

    /**
     * 制作iTree
     *
     * @param listData 列出数据
     * @param rootId   根 ID
     */
    public static <E extends ITreeNode<E>> void makeITree(List<E> listData, String rootId) {
        if (StrUtil.isBlank(rootId)) {
            makeTree(listData, ITreeNode::getId, ITreeNode::getParentId, ITreeNode::setChildren, null);
            return;
        }
        makeTree(listData, ITreeNode::getId, ITreeNode::getParentId, ITreeNode::setChildren, x -> rootId.equals(x.getId()));
    }

    /**
     * 制作树
     *
     * @param listData       列出数据
     * @param idFunc         E::getId
     * @param pidFunc        E::getParentId
     * @param setSubChildren E::setChildren
     * @param rootPredicate  判断E中为根节点的条件x->x.getPId()==-1L , x->x.getParentId()==null,x->x.getParentMenuId()==0
     */
    public static <K, E> void makeTree(List<E> listData, Function<E, K> idFunc, Function<E, K> pidFunc, BiConsumer<E, List<E>> setSubChildren, Predicate<E> rootPredicate) {
        if (CollUtil.isEmpty(listData)) {
            return;
        }
        Map<K, List<E>> pIdMap = listData.stream().collect(Collectors.groupingBy(pidFunc));

        listData.forEach(data -> {
            setSubChildren.accept(data, pIdMap.get(idFunc.apply(data)));
        });
        if (rootPredicate == null) {
            Set<K> allIdList = listData.stream().map(idFunc).collect(Collectors.toSet());
            listData.removeIf(data -> allIdList.contains(pidFunc.apply(data)));
            return;
        }
        listData.removeIf(rootPredicate);
    }

    /**
     * 制作树克隆，不改变原数据
     *
     * @param listData 列出数据
     * @param rootId   根ID,如果为空，则自动查询根节点
     * @return {@link List }<{@link E }>
     */
    public static <E extends BaseTreeNode<E>> List<E> makeTreeClone(List<E> listData, String rootId) {
        if (CollUtil.isEmpty(listData)) {
            return List.of();
        }
        List<E> treeList = CollUtil.newArrayList(listData);
        makeTree(treeList, rootId);
        return treeList;
    }

    /**
     * 后续遍历
     * <p>后序遍历（Post-order Traversal）是先遍历所有的子节点，然后访问根节点。</p>
     *
     * @param treeList 树列表
     * @param consumer 消费者 x->System.out.println(x.getId())
     */
    public static <E extends BaseTreeNode<E>> void forPostOrder(List<E> treeList, Consumer<E> consumer) {
        forPostOrder(treeList, BaseTreeNode::getChildren, consumer);
    }

    /**
     * 后续遍历
     * <p>后序遍历（Post-order Traversal）是先遍历所有的子节点，然后访问根节点。</p>
     *
     * @param treeList    树列表
     * @param getChildren 获取子项 E::getChildren
     * @param consumer    消费者 x->System.out.println(x.getId())
     */
    public static <E> void forPostOrder(List<E> treeList, Function<E, List<E>> getChildren, Consumer<E> consumer) {
        if (CollUtil.isEmpty(treeList)) {
            return;
        }
        for (E node : treeList) {
            List<E> childList = getChildren.apply(node);
            if (CollUtil.isNotEmpty(childList)) {
                forPostOrder(childList, getChildren, consumer);
            }
            consumer.accept(node);
        }
    }

    /**
     * 前序遍历
     * <p>前序遍历（Pre-order Traversal）是访问根节点，然后分别遍历每一个子节点。</p>
     *
     * @param treeList 树列表
     * @param consumer 消费者
     */
    public static <E extends BaseTreeNode<E>> void forPreOrder(List<E> treeList, Consumer<E> consumer) {
        forPreOrder(treeList, BaseTreeNode::getChildren, consumer);
    }

    /**
     * 前序遍历
     * <p>前序遍历（Pre-order Traversal）是访问根节点，然后分别遍历每一个子节点。</p>
     *
     * @param treeList    树列表
     * @param getChildren 获取子项E::getChildren
     * @param consumer    消费者 x->System.out.println(x.getId())
     */
    public static <E> void forPreOrder(List<E> treeList, Function<E, List<E>> getChildren, Consumer<E> consumer) {
        if (CollUtil.isEmpty(treeList)) {
            return;
        }
        for (E node : treeList) {
            consumer.accept(node);
            List<E> childList = getChildren.apply(node);
            if (CollUtil.isNotEmpty(childList)) {
                forPreOrder(childList, getChildren, consumer);
            }
        }
    }

    /**
     * 层序遍历(广度优先BFS)
     *
     * @param treeList 树列表
     * @param consumer 消费者 x->System.out.println(x.getId())
     */
    public static <E extends BaseTreeNode<E>> void forLevelOrder(List<E> treeList, Consumer<E> consumer) {
        forLevelOrder(treeList, BaseTreeNode::getChildren, consumer);
    }

    /**
     * 层序遍历(广度优先BFS)
     *
     * @param treeList    树列表
     * @param getChildren 获取子项E::getChildren
     * @param consumer    消费者 x->System.out.println(x.getId())
     */
    public static <E extends BaseTreeNode<E>> void forLevelOrder(List<E> treeList, Function<E, List<E>> getChildren, Consumer<E> consumer) {
        //
        Queue<E> queue = new LinkedList<>(treeList);
        while (!queue.isEmpty()) {
            E item = queue.poll();
            consumer.accept(item);
            List<E> childList = getChildren.apply(item);
            if (CollUtil.isNotEmpty(childList)) {
                queue.addAll(childList);
            }
        }
    }

    /**
     * 把树转化为平铺的列表
     *
     * @param treeList 树列表
     * @return {@link List }<{@link E }>
     */
    public static <E extends BaseTreeNode<E>> List<E> flatten(List<E> treeList) {
        return flatten(treeList, BaseTreeNode::getChildren);
    }

    /**
     * 把树转化为平铺的列表
     *
     * @param treeList 树列表
     * @return {@link List }<{@link E }>
     */
    public static <E> List<E> flatten(List<E> treeList, Function<E, List<E>> getChildren) {
        List<E> listData = new ArrayList<>();
        forPreOrder(treeList, getChildren, listData::add);
        return listData;
    }
}
