package cn.fanzy.flow.model.flow;

import cn.fanzy.flow.model.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 流节点
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowNode implements Serializable {
    @Serial
    private static final long serialVersionUID = 2472835976635921220L;

    /**
     * 编号
     */
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名字
     */
    private String name;

    /**
     * 节点类型
     */
    private NodeType nodeType;

    /**
     * 从 ID
     */
    private String fromId;

    /**
     * 到 ID
     */
    private String toId;

    /**
     * 序号
     */
    private Integer orderNumber;

    /**
     * 处理人集合
     */
    private List<FlowNodeHandler> handlers;
    /**
     * 数据范围
     */
    private FlowNodeDataScope dataScope;

    /**
     * 条件
     */
    private FlowNodeCondition condition;
}
