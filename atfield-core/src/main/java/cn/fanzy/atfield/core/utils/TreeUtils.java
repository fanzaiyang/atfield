package cn.fanzy.atfield.core.utils;


import cn.fanzy.atfield.core.model.BaseTreeNode;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.BiConsumer;
import org.springframework.cglib.core.internal.Function;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树工具类,请使用：{@link cn.fanzy.atfield.core.utils.tree.TreeUtils}
 *
 * @author fanzaiyang
 * @date 2024/01/23
 */
@Deprecated
@Slf4j
public class TreeUtils extends TreeUtil {
    /**
     * 根父 ID
     */
    public static final String ROOT_PARENT_ID = "-1";

    /**
     * 构建树 默认为：id，parentId，name，orderNumber 根节点是：-1
     *
     * @param data List数据
     * @param <E>  类型
     * @return List树结构
     */
    public static <E> List<Tree<String>> buildTree(List<E> data) {
        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = buildNodeList(data, "id", "parentId", "name", "orderNumber", "-1");
        return build(nodeList, "-1");
    }

    /**
     * 构建树
     * 默认为：id，parentId，name，orderNumber
     *
     * @param data   数据
     * @param <E>    类型
     * @param rootId 根id,默认-1
     * @return List
     */
    public static <E> List<Tree<String>> buildTree(List<E> data, String rootId) {
        TimeInterval timer = DateUtil.timer();
        rootId = StrUtil.blankToDefault(rootId, "-1");
        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = buildNodeList(data, "id", "parentId", "name", "orderNumber", rootId);
        List<Tree<String>> build = build(nodeList, rootId);
        long end = System.currentTimeMillis();
        log.debug("list转tree耗时(秒):{}", timer.intervalSecond());
        return build;
    }

    /**
     * 构建树
     * 默认为：id，parentId，name，orderNumber
     *
     * @param data      数据
     * @param idKey     id关键，默认：id
     * @param parentKey 上级的关键,默认：parentId
     * @param nameKey   名字的关键，默认：name
     * @param weightKey 顺序的关键，默认：orderNumber
     * @param rootId    根id,默认-1
     * @param <E>       类型
     * @return List
     */
    public static <E> List<Tree<String>> buildTree(List<E> data, String idKey, String parentKey, String nameKey, String weightKey, String rootId) {
        rootId = StrUtil.blankToDefault(rootId, "-1");
        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = buildNodeList(data, idKey, parentKey, nameKey, weightKey, rootId);
        return build(nodeList, rootId);
    }

    private static <E> List<cn.hutool.core.lang.tree.TreeNode<String>> buildNodeList(List<E> data, String idKey, String parentKey, String nameKey, String weightKey, String rootId) {
        if (CollUtil.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = CollUtil.newArrayList();
        for (int i = 0; i < data.size(); i++) {
            E datum = data.get(i);
            Map<String, Object> object = BeanUtil.beanToMap(datum);
            cn.hutool.core.lang.tree.TreeNode<String> node = new cn.hutool.core.lang.tree.TreeNode<>();
            node.setId(object.getOrDefault(StrUtil.blankToDefault(idKey, "id"), "") + "");
            node.setParentId(object.getOrDefault(StrUtil.blankToDefault(parentKey, "parentId"), "") + "");
            node.setName(object.getOrDefault(StrUtil.blankToDefault(nameKey, "name"), "") + "");
            Object orderNumber = object.getOrDefault(StrUtil.blankToDefault(weightKey, "orderNumber"), i);
            node.setWeight(NumberUtil.isNumber(orderNumber + "") ? NumberUtil.parseInt(orderNumber + "") : i);
            object.put("unionId", node.getId());
            object.put("originParentId", node.getParentId());
            object.remove(StrUtil.blankToDefault(idKey, "id"));
            object.remove(StrUtil.blankToDefault(parentKey, "parentId"));
            node.setExtra(object);
            nodeList.add(node);
        }
        // 这里是修改根结点
        Set<String> nodeIdSet = nodeList.stream().map(cn.hutool.core.lang.tree.TreeNode::getId).collect(Collectors.toSet());
        rootId = StrUtil.blankToDefault(rootId, StrUtil.blankToDefault(rootId, "-1"));
        String finalRootId = rootId;
        nodeList.forEach(item -> {
            if (!nodeIdSet.contains(item.getParentId())) {
                item.setParentId(finalRootId);
            }
        });
        return nodeList;
    }

    /**
     * 合成树
     *
     * @param sourceList 源列表
     * @return {@link List }<{@link E }>
     */
    public static <T, E extends BaseTreeNode<E>> List<E> makeTree(List<E> sourceList) {
        List<String> idList = sourceList.stream().map(E::getId).toList();
        return makeTree(sourceList, E::getParentId, E::getId, x -> !CollUtil.contains(idList, x.getParentId()), E::setChildren);
    }

    /**
     * 合成树
     *
     * @param sourceList     需要合成树的List
     * @param pId            对象中的父ID字段,如:Menu:getPid
     * @param id             对象中的id字段 ,如：Menu:getId
     * @param rootCheck      判断E中为根节点的条件，如：x->x.getPId()==-1L , x->x.getParentId()==null,x->x.getParentMenuId()==0
     * @param setSubChildren E中设置下级数据方法，如： Menu::setSubMenus
     * @param <T>            ID字段类型
     * @param <E>            泛型实体对象
     * @return List<E> 合成后的树
     */
    public static <T, E> List<E> makeTree(List<E> sourceList, java.util.function.Function<E, T> pId, java.util.function.Function<E, T> id, Predicate<E> rootCheck, java.util.function.BiConsumer<E, List<E>> setSubChildren) {
        //按原数组顺序构建父级数据Map，使用Optional考虑pId为null
        Map<Optional<T>, List<E>> parentMenuMap = sourceList.stream().collect(Collectors.groupingBy(
                node -> Optional.ofNullable(pId.apply(node)),
                LinkedHashMap::new,
                Collectors.toList()
        ));
        List<E> result = new ArrayList<>();
        for (E node : sourceList) {
            //添加到下级数据中
            setSubChildren.accept(node, parentMenuMap.get(Optional.ofNullable(id.apply(node))));
            //如里是根节点，加入结构
            if (rootCheck.test(node)) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 树中过滤
     *
     * @param tree        需要过滤的树
     * @param predicate   过滤条件
     * @param getChildren 获取下级数据方法，如：MenuVo::getSubMenus
     * @param <E>         泛型实体对象
     * @return List<E> 过滤后的树
     */
    public static <E> List<E> filter(List<E> tree, Predicate<E> predicate, java.util.function.Function<E, List<E>> getChildren) {
        return tree.stream().filter(item -> {
            if (predicate.test(item)) {
                List<E> children = getChildren.apply(item);
                if (children != null && !children.isEmpty()) {
                    filter(children, predicate, getChildren);
                }
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }


    /**
     * 树中搜索
     *
     * @param tree           需要查询的树
     * @param predicate      查询条件
     * @param getSubChildren 获取下级数据方法，如：MenuVo::getSubMenus
     * @param <E>            泛型实体对象
     * @return 返回搜索到的节点及其父级到根节点
     */
    public static <E> List<E> search(List<E> tree, Predicate<E> predicate, java.util.function.Function<E, List<E>> getSubChildren) {
        Iterator<E> iterator = tree.iterator();
        while (iterator.hasNext()) {
            E item = iterator.next();
            List<E> childList = getSubChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                search(childList, predicate, getSubChildren);
            }
            if (!predicate.test(item) && (childList == null || childList.isEmpty())) {
                iterator.remove();
            }
        }
        return tree;
    }

    /**
     * 将list合成树 -> E必须继承{@link BaseTreeNode}
     * 大数据下有性能问题，建议使用 {@link TreeNewBeeUtils}
     * <pre>
     *     TreeUtils.makeTree(list, OrgTreeVo::setChildren);
     * </pre>
     *
     * @param list           需要合成树的List
     * @param setSubChildren E中设置下级数据方法，如：Menu::setSubMenus
     * @return {@link List }<{@link E }>
     */
    @Deprecated(since = "1.1.7")
    public static <E extends BaseTreeNode<E>> List<E> makeTree(List<E> list, BiConsumer<E, List<E>> setSubChildren) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<String> idList = list.stream().map(BaseTreeNode::getId).toList();
        return list.stream().filter(a -> !idList.contains(a.getParentId()))
                .peek(x -> setSubChildren.accept(x,
                                makeChildren(x, list,
                                        (a, b) -> StrUtil.equalsIgnoreCase(a.getId(), b.getParentId()),
                                        setSubChildren)
                        )
                )
                .collect(Collectors.toList());
    }

    /**
     * 将list合成树
     * 大数据下有性能问题，建议使用 {@link TreeNewBeeUtils}
     * <pre>
     *     TreeUtils.makeTree(list,
     *     a->a.getParentId().equals("-1") ,
     *     (a, b) -> StrUtil.equalsIgnoreCase(a.getId(), b.getParentId()),
     *     OrgTreeVo::setChildren);
     * </pre>
     *
     * @param list           需要合成树的List
     * @param rootCheck      判断E中为根节点的条件，如：x->x.getPId()==-1L , x->x.getParentId()==null,x->x.getParentMenuId()==0
     * @param parentCheck    判断E中为父节点条件，如：(x,y)->x.getId().equals(y.getPId())
     * @param setSubChildren E中设置下级数据方法，如：Menu::setSubMenus
     * @param <E>            泛型实体对象
     * @return 合成好的树
     */
    @Deprecated(since = "1.1.7")
    public static <E> List<E> makeTree(List<E> list, Predicate<E> rootCheck, BiFunction<E, E, Boolean> parentCheck, BiConsumer<E, List<E>> setSubChildren) {
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().filter(rootCheck)
                .peek(x -> setSubChildren.accept(x, makeChildren(x, list, parentCheck, setSubChildren)))
                .collect(Collectors.toList());
    }

    /**
     * 将树打平成tree -> E必须继承{@link BaseTreeNode}
     * <pre>
     *     TreeUtils.flat(treeList,  x -> x.setChildren(null))
     * </pre>
     *
     * @param tree           需要打平的树
     * @param setSubChildren 将下级数据置空方法，如：x->x.setSubMenus(null)
     * @param <E>            泛型实体对象
     * @return 打平后的数据
     */
    public static <E extends BaseTreeNode<E>> List<E> flat(List<E> tree, Consumer<E> setSubChildren) {
        List<E> res = new ArrayList<>();
        forPostOrder(tree, item -> {
            setSubChildren.accept(item);
            res.add(item);
        }, BaseTreeNode::getChildren);
        return res;
    }

    /**
     * 将树打平成tree
     * <pre>
     *     TreeUtils.flat(treeList, OrgTreeVo::getChildren, x -> x.setChildren(null))
     * </pre>
     *
     * @param tree           需要打平的树
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param setSubChildren 将下级数据置空方法，如：x->x.setSubMenus(null)
     * @param <E>            泛型实体对象
     * @return 打平后的数据
     */
    public static <E> List<E> flat(List<E> tree, Function<E, List<E>> getSubChildren, Consumer<E> setSubChildren) {
        List<E> res = new ArrayList<>();
        forPostOrder(tree, item -> {
            setSubChildren.accept(item);
            res.add(item);
        }, getSubChildren);
        return res;
    }


    /**
     * 前序遍历
     * <pre>
     *        0
     *     1       2
     *  3    4  5     6
     * 7  8 9
     * 前序：0137849256
     * 后序：3718495260
     * 层序：0123456789
     *     TreeUtils.forPreOrder(treeList, x -> System.out.println(x.getName()), OrgTreeVo::getChildren);
     * </pre>
     *
     * @param tree           需要遍历的树
     * @param consumer       遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param <E>            泛型实体对象
     */
    @Deprecated
    public static <E> void forPreOrder(List<E> tree, Consumer<E> consumer, Function<E, List<E>> getSubChildren) {
        for (E l : tree) {
            consumer.accept(l);
            List<E> es = getSubChildren.apply(l);
            if (es != null && !es.isEmpty()) {
                forPreOrder(es, consumer, getSubChildren);
            }
        }
    }

    /**
     * 前序遍历
     * <pre>
     *        0
     *     1       2
     *  3    4  5     6
     * 7  8 9
     * 前序：0137849256
     * 后序：3718495260
     * 层序：0123456789
     *     TreeUtils.forPreOrder(treeList, OrgTreeVo::getChildren, x -> System.out.println(x.getName()));
     * </pre>
     *
     * @param tree           需要遍历的树
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param consumer       遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
     * @param <E>            泛型实体对象
     */
    public static <E> void forPreOrder(List<E> tree, Function<E, List<E>> getSubChildren, Consumer<E> consumer) {
        for (E l : tree) {
            consumer.accept(l);
            List<E> es = getSubChildren.apply(l);
            if (es != null && !es.isEmpty()) {
                forPreOrder(es, consumer, getSubChildren);
            }
        }
    }

    /**
     * 层序遍历
     * <pre>
     *        0
     *     1       2
     *  3    4  5     6
     * 7  8 9
     * 前序：0137849256
     * 后序：3718495260
     * 层序：0123456789
     *     TreeUtils.forLevelOrder(treeList, x -> System.out.println(x.getName()), OrgTreeVo::getChildren);
     * </pre>
     *
     * @param tree           需要遍历的树
     * @param consumer       遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param <E>            泛型实体对象
     */
    @Deprecated
    public static <E> void forLevelOrder(List<E> tree, Consumer<E> consumer, Function<E, List<E>> getSubChildren) {
        Queue<E> queue = new LinkedList<>(tree);
        while (!queue.isEmpty()) {
            E item = queue.poll();
            consumer.accept(item);
            List<E> childList = getSubChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                queue.addAll(childList);
            }
        }
    }

    /**
     * 层序遍历
     * <pre>
     *        0
     *     1       2
     *  3    4  5     6
     * 7  8 9
     * 前序：0137849256
     * 后序：3718495260
     * 层序：0123456789
     *     TreeUtils.forLevelOrder(treeList, OrgTreeVo::getChildren, x -> System.out.println(x.getName()));
     * </pre>
     *
     * @param tree           需要遍历的树
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param consumer       遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
     * @param <E>            泛型实体对象
     */
    public static <E> void forLevelOrder(List<E> tree, Function<E, List<E>> getSubChildren, Consumer<E> consumer) {
        Queue<E> queue = new LinkedList<>(tree);
        while (!queue.isEmpty()) {
            E item = queue.poll();
            consumer.accept(item);
            List<E> childList = getSubChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                queue.addAll(childList);
            }
        }
    }


    /**
     * 后序遍历
     * <pre>
     *        0
     *     1       2
     *  3    4  5     6
     * 7  8 9
     * 前序：0137849256
     * 后序：3718495260
     * 层序：0123456789
     *     TreeUtils.forPostOrder(treeList, x -> System.out.println(x.getName()), OrgTreeVo::getChildren);
     * </pre>
     *
     * @param tree           需要遍历的树
     * @param consumer       遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param <E>            泛型实体对象
     */
    @Deprecated
    public static <E> void forPostOrder(List<E> tree, Consumer<E> consumer, Function<E, List<E>> getSubChildren) {
        for (E item : tree) {
            List<E> childList = getSubChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                forPostOrder(childList, consumer, getSubChildren);
            }
            consumer.accept(item);
        }
    }

    /**
     * 后序遍历
     * <pre>
     *        0
     *     1       2
     *  3    4  5     6
     * 7  8 9
     * 前序：0137849256
     * 后序：3718495260
     * 层序：0123456789
     *     TreeUtils.forPostOrder(treeList, OrgTreeVo::getChildren, x -> System.out.println(x.getName()));
     * </pre>
     *
     * @param tree           需要遍历的树
     * @param getSubChildren 设置下级数据方法，如：Menu::getSubMenus
     * @param consumer       遍历后对单个元素的处理方法，如：x-> System.out.println(x)、 System.out::println打印元素
     * @param <E>            泛型实体对象
     */
    public static <E> void forPostOrder(List<E> tree, Function<E, List<E>> getSubChildren, Consumer<E> consumer) {
        for (E item : tree) {
            List<E> childList = getSubChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                forPostOrder(childList, consumer, getSubChildren);
            }
            consumer.accept(item);
        }
    }

    /**
     * 对树所有子节点按comparator排序
     * <pre>
     *     TreeUtils.sort(treeList, Comparator.comparing(OrgTreeVo::getId), OrgTreeVo::getChildren);
     *     TreeUtils.sort(treeList, (x,y)->y.getRank().compareTo(x.getRank()), MenuVo::getSubMenus);
     * </pre>
     *
     * @param tree        需要排序的树
     * @param comparator  排序规则Comparator，如：Comparator.comparing(MenuVo::getRank)按Rank正序 ,(x,y)->y.getRank().compareTo(x.getRank())，按Rank倒序
     * @param getChildren 获取下级数据方法，如：MenuVo::getSubMenus
     * @param <E>         泛型实体对象
     * @return 排序好的树
     */
    @Deprecated
    public static <E> List<E> sort(List<E> tree, Comparator<? super E> comparator, Function<E, List<E>> getChildren) {
        for (E item : tree) {
            List<E> childList = getChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                sort(childList, comparator, getChildren);
            }
        }
        tree.sort(comparator);
        return tree;
    }

    /**
     * 对树所有子节点按comparator排序
     * <pre>
     *     TreeUtils.sort(treeList, OrgTreeVo::getChildren, Comparator.comparing(OrgTreeVo::getId));
     *     TreeUtils.sort(treeList, MenuVo::getSubMenus, (x,y)->y.getRank().compareTo(x.getRank()));
     * </pre>
     *
     * @param tree        需要排序的树
     * @param getChildren 获取下级数据方法，如：MenuVo::getSubMenus
     * @param comparator  排序规则Comparator，如：Comparator.comparing(MenuVo::getRank)按Rank正序 ,(x,y)->y.getRank().compareTo(x.getRank())，按Rank倒序
     * @param <E>         泛型实体对象
     * @return 排序好的树
     */
    public static <E> List<E> sort(List<E> tree, Function<E, List<E>> getChildren, Comparator<? super E> comparator) {
        for (E item : tree) {
            List<E> childList = getChildren.apply(item);
            if (childList != null && !childList.isEmpty()) {
                sort(childList, comparator, getChildren);
            }
        }
        tree.sort(comparator);
        return tree;
    }

    @Deprecated(since = "1.1.7")
    private static <E> List<E> makeChildren(E parent, List<E> allData, BiFunction<E, E, Boolean> parentCheck, BiConsumer<E, List<E>> children) {
        return allData.stream()
                .filter(x -> parentCheck.apply(parent, x))
                .peek(x -> children.accept(x, makeChildren(x, allData, parentCheck, children)))
                .collect(Collectors.toList());
    }
}
