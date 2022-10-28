package cn.fanzy.breeze.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * util树
 *
 * @author fanzaiyang
 * @date 2021/06/16
 */
@Slf4j
public class TreeUtils extends TreeUtil {
    /**
     * 构建树
     * 默认为：id，parentId，name，orderNumber
     * 根节点是：-1
     *
     * @param data 数据
     * @return {@link List<Tree<String>>}
     */
    public static <T> List<Tree<String>> buildTree(List<T> data) {
        List<TreeNode<String>> nodeList = buildNodeList(data, "id", "parentId", "name", "orderNumber", BreezeConstants.TREE_ROOT_ID);
        return build(nodeList, BreezeConstants.TREE_ROOT_ID);
    }

    /**
     * 构建树
     * 默认为：id，parentId，name，orderNumber
     *
     * @param data   数据
     * @param rootId 根id,默认-1
     * @return {@link List<Tree<String>>}
     */
    public static <T> List<Tree<String>> buildTree(List<T> data, String rootId) {
        TimeInterval timer = DateUtil.timer();
        List<TreeNode<String>> nodeList = buildNodeList(data, "id", "parentId", "name", "orderNumber", BreezeConstants.TREE_ROOT_ID);
        List<Tree<String>> build = build(nodeList, StrUtil.blankToDefault(rootId, BreezeConstants.TREE_ROOT_ID));
        long end = System.currentTimeMillis();
        log.info("list转tree耗时(秒):{}", timer.intervalSecond());
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
     * @return {@link List<Tree<String>>}
     */
    public static <T> List<Tree<String>> buildTree(List<T> data, String idKey, String parentKey, String nameKey, String weightKey, String rootId) {
        List<TreeNode<String>> nodeList = buildNodeList(data, idKey, parentKey, nameKey, weightKey, BreezeConstants.TREE_ROOT_ID);
        return build(nodeList, StrUtil.blankToDefault(rootId, BreezeConstants.TREE_ROOT_ID));
    }

    private static <T> List<TreeNode<String>> buildNodeList(List<T> data, String idKey, String parentKey, String nameKey, String weightKey, String rootId) {
        if (CollUtil.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        for (int i = 0; i < data.size(); i++) {
            T datum = data.get(i);
            Map<String, Object> object = BeanUtil.beanToMap(datum);
            TreeNode<String> node = new TreeNode<>();
            node.setId(object.getOrDefault(StrUtil.blankToDefault(idKey, "id"), "") + "");
            node.setParentId(object.getOrDefault(StrUtil.blankToDefault(parentKey, "parentId"), "") + "");
            node.setName(object.getOrDefault(StrUtil.blankToDefault(nameKey, "name"), "") + "");
            String orderNumber = StrUtil.blankToDefault(weightKey, "orderNumber");
            node.setWeight(NumberUtil.isNumber(orderNumber) ? orderNumber : i);
            object.put("unionId", node.getId());
            object.put("originParentId", node.getParentId());
            object.remove(StrUtil.blankToDefault(idKey, "id"));
            object.remove(StrUtil.blankToDefault(parentKey, "parentId"));
            node.setExtra(object);
            nodeList.add(node);
        }
        // 这里是修改根结点
        Set<String> nodeIdSet = nodeList.stream().map(TreeNode::getId).collect(Collectors.toSet());
        rootId = StrUtil.blankToDefault(rootId, BreezeConstants.TREE_ROOT_ID);
        String finalRootId = rootId;
        nodeList.forEach(item -> {
            if (!nodeIdSet.contains(item.getParentId())) {
                item.setParentId(finalRootId);
            }
        });
        return nodeList;
    }
}
