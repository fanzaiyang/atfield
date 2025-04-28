package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.model.BaseTreeNode;
import cn.hutool.core.collection.CollUtil;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 新树工具,请使用：{@link cn.fanzy.atfield.core.utils.tree.TreeUtils}
 *
 * @author fanzaiyang
 * @date 2025/01/15
 */
@Deprecated
public class TreeNewBeeUtils {
    /**
     * 根父 ID
     */
    public static final String ROOT_PARENT_ID = "-1";

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
    public static <T, E> List<E> makeTree(List<E> sourceList, Function<E, T> pId, Function<E, T> id, Predicate<E> rootCheck, BiConsumer<E, List<E>> setSubChildren) {
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
    public static <E> List<E> filter(List<E> tree, Predicate<E> predicate, Function<E, List<E>> getChildren) {
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
    public static <E> List<E> search(List<E> tree, Predicate<E> predicate, Function<E, List<E>> getSubChildren) {
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
}
