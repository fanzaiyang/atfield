package cn.fanzy.atfield.core.utils.tree;

import cn.fanzy.atfield.core.model.BaseTreeNode;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
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
     *
     * @param listData           数据
     * @param isChangeOriginData 是否更改源数据；true：会修改参数listData,此方式效率也是最高。
     * @return {@link List }<{@link T }> 新的集合
     */
    public static <T extends BaseTreeNode<T>> List<T> getRootNodeList(List<T> listData, boolean isChangeOriginData) {
        // 检查pid是否存在于id集合中
        Set<String> allIdList = listData.stream()
                .map(BaseTreeNode::getId).collect(Collectors.toSet());
        // 更改源数据，效率更改
        if (isChangeOriginData) {
            listData.removeIf(data -> allIdList.contains(data.getParentId()));
            return listData;
        }
        return listData.stream().filter(data -> !allIdList.contains(data.getParentId())).toList();
    }

    /**
     * 获取根节点列表
     * <p>要求传入的类中包含id、parentId字段</p>
     *
     * @param listData           列出数据
     * @param idField            id 字段
     * @param parentIdField      父 ID 字段
     * @param isChangeOriginData 是更改源数据
     * @return {@link List }<{@link E }>
     */
    public static <E> List<E> getRootNodeList(List<E> listData, String idField, String parentIdField, boolean isChangeOriginData) {
        String finalIdField = StrUtil.blankToDefault(idField, "id");
        String finalParentIdField = StrUtil.blankToDefault(parentIdField, "parentId");
        // 检查pid是否存在于id集合中
        Set<String> allIdList = listData.stream()
                .map(data -> ReflectUtil.getFieldValue(data, finalIdField).toString()).collect(Collectors.toSet());
        // 更改源数据，效率更改
        if (isChangeOriginData) {
            listData.removeIf(data -> allIdList.contains(ReflectUtil.getFieldValue(data, finalParentIdField).toString()));
            return listData;
        }
        return listData.stream().filter(data -> !allIdList.contains(ReflectUtil.getFieldValue(data, finalParentIdField).toString())).toList();
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
        if (CollUtil.isEmpty(listData)) {
            return;
        }

        Map<String, List<E>> pIdMap = listData.stream().collect(Collectors.groupingBy(BaseTreeNode::getParentId));

        listData.forEach(data -> {
            data.setChildren(pIdMap.get(data.getId()));
        });
        if (StrUtil.isBlank(rootId)) {
            Set<String> allIdList = listData.stream()
                    .map(BaseTreeNode::getId).collect(Collectors.toSet());
            listData.removeIf(data -> allIdList.contains(data.getParentId()));
            return;
        }
        listData.removeIf(data -> !rootId.equals(data.getParentId()));
    }

    /**
     * 制作树
     *
     * @param listData       列出数据
     * @param idFun          E::getId
     * @param pidFun         E::getParentId
     * @param setSubChildren E::setChildren
     * @param rootPredicate  判断E中为根节点的条件x->x.getPId()==-1L , x->x.getParentId()==null,x->x.getParentMenuId()==0
     */
    public static <T, E> void makeTree(List<E> listData, Function<E, T> idFun,
                                       Function<E, T> pidFun,
                                       BiConsumer<E, List<E>> setSubChildren,
                                       Predicate<E> rootPredicate) {
        if (CollUtil.isEmpty(listData)) {
            return;
        }
        Map<T, List<E>> pIdMap = listData.stream().collect(Collectors.groupingBy(pidFun));

        listData.forEach(data -> {
            setSubChildren.accept(data, pIdMap.get(idFun.apply(data)));
        });
        if (rootPredicate == null) {
            Set<T> allIdList = listData.stream().map(idFun).collect(Collectors.toSet());
            listData.removeIf(data -> allIdList.contains(pidFun.apply(data)));
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
     * @param consumer 消费者
     */
    public static <E extends BaseTreeNode<E>> void forPostOrder(List<E> treeList, Consumer<E> consumer) {
        if (CollUtil.isEmpty(treeList)) {
            return;
        }
        for (E node : treeList) {
            if (CollUtil.isNotEmpty(node.getChildren())) {
                forPostOrder(node.getChildren(), consumer);
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
        if (CollUtil.isEmpty(treeList)) {
            return;
        }
        for (E node : treeList) {
            consumer.accept(node);
            if (CollUtil.isNotEmpty(node.getChildren())) {
                forPreOrder(node.getChildren(), consumer);
            }
        }
    }

    /**
     * 层序遍历(广度优先BFS)
     *
     * @param treeList 树列表
     * @param consumer 消费者
     */
    public static <E extends BaseTreeNode<E>> void forLevelOrder(List<E> treeList, Consumer<E> consumer) {
        //
        Queue<E> queue = new LinkedList<>(treeList);
        while (!queue.isEmpty()) {
            E item = queue.poll();
            consumer.accept(item);
            if (CollUtil.isNotEmpty(item.getChildren())) {
                queue.addAll(item.getChildren());
            }
        }
    }

    /**
     * 把树转化为平铺的列表
     *
     * @param treeList 列出数据
     * @return {@link List }<{@link E }>
     */
    public static <E extends BaseTreeNode<E>> List<E> flatten(List<E> treeList) {
        List<E> listData = new ArrayList<>();
        forPreOrder(treeList, listData::add);
        return listData;
    }
}
