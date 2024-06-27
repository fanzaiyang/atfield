package cn.fanzy.smart.flow.utils;

import cn.fanzy.smart.flow.exception.BadFlowDataException;
import cn.fanzy.smart.flow.model.entity.FlowTaskInfoEntity;
import cn.fanzy.smart.flow.model.enums.NodeType;
import cn.fanzy.smart.flow.model.flow.FlowNode;
import cn.fanzy.smart.flow.model.flow.FlowNodeHandler;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 流数据解析实用程序
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
public class FlowParseUtil {

    /**
     * 根据流程数据创建流节点
     *
     * @param flowData 流量数据
     * @return {@link List}<{@link FlowNode}>
     */
    public static List<FlowNode> parseFlowNodes(String flowData) {
        if (JSONUtil.isTypeJSONArray(flowData)) {
            throw new BadFlowDataException("F001", "流程数据不符合要求！");
        }
        try {
            List<FlowNode> nodeList = JSONUtil.toList(flowData, FlowNode.class);
            if (CollUtil.isEmpty(nodeList)) {
                throw new BadFlowDataException("F003", "流程数据不能为空！");
            }
            for (FlowNode node : nodeList) {
                if (node.getNodeType().equals(NodeType.USER_TASK) || node.getNodeType().equals(NodeType.CC_TASK)) {
                    if (CollUtil.isEmpty(node.getHandlers())) {
                        throw new BadFlowDataException("F004", "节点【" + node.getId() + "】的处理人不能为空！");
                    }
                }
            }
            return nodeList;
        } catch (Exception e) {
            throw new BadFlowDataException("F002", "流程数据解析失败！");
        }
    }

    /**
     * 根据节点ID生成此节点的待办任务
     *
     * @param flowData 流量数据
     * @param nodeId   节点 ID
     * @return {@link List}<{@link FlowTaskInfoEntity}>
     */
    public static List<FlowTaskInfoEntity> parseFlowTask(String flowData, String nodeId) {
        List<FlowNode> flowNodeList = parseFlowNodes(flowData);
        FlowNode node = getFlowNodeById(flowNodeList, nodeId);
        FlowNode nextNode = getFlowNextNodeById(flowNodeList, nodeId);
        List<FlowTaskInfoEntity> taskList = new ArrayList<>();
        // 根据节点信息生成任务
        if (CollUtil.isEmpty(node.getHandlers())) {
            taskList.add(FlowTaskInfoEntity.builder()
                    .nextNodeId(nextNode == null ? null : nextNode.getId())
                    .nodeId(node.getId())
                    .nodeType(node.getNodeType())
                    .receivedTime(LocalDateTime.now())
                    .orderNumber(1)
                    .build());
            return taskList;
        }
        for (FlowNodeHandler handler : node.getHandlers()) {
            taskList.add(FlowTaskInfoEntity.builder()
                    .nextNodeId(nextNode == null ? null : nextNode.getId())
                    .nodeId(node.getId())
                    .nodeType(node.getNodeType())
                    .handlerId(handler.getId())
                    .receivedTime(LocalDateTime.now())
                    .orderNumber(1)
                    .build());
        }
        return taskList;
    }


    public static FlowNode getFlowNodeById(List<FlowNode> nodeList, String nodeId) {
        Optional<FlowNode> first = nodeList.stream().filter(flowNode -> nodeId.equals(flowNode.getId())).findFirst();
        if (first.isEmpty()) {
            throw new BadFlowDataException("F004", "节点ID不存在！" + nodeId);
        }
        return first.get();
    }

    /**
     * 按 ID 获取流节点
     *
     * @param flowData 流量数据
     * @param nodeId   节点 ID
     * @return {@link FlowNode}
     */
    public static FlowNode getFlowNodeById(String flowData, String nodeId) {
        return getFlowNodeById(parseFlowNodes(flowData), nodeId);
    }

    /**
     * 按 ID 获取流下一个节点
     *
     * @param nodeList 节点列表
     * @param nodeId   节点 ID
     * @return {@link FlowNode}
     */
    public static FlowNode getFlowNextNodeById(List<FlowNode> nodeList, String nodeId) {
        int indexOf = CollUtil.indexOf(nodeList, node -> StrUtil.equals(node.getId(), nodeId));
        Assert.isTrue(indexOf != -1, "节点ID不存在！" + nodeId);
        try {
            return nodeList.get(indexOf + 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按 ID 获取流下一个节点
     *
     * @param flowData 流量数据
     * @param nodeId   节点 ID
     * @return {@link FlowNode}
     */
    public static FlowNode getFlowNextNodeById(String flowData, String nodeId) {
        return getFlowNextNodeById(parseFlowNodes(flowData), nodeId);
    }
}
