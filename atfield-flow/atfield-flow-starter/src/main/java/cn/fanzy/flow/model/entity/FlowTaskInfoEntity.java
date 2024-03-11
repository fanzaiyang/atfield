package cn.fanzy.flow.model.entity;

import cn.fanzy.flow.model.enums.ApproveOrder;
import cn.fanzy.flow.model.enums.ApproveResult;
import cn.fanzy.flow.model.enums.NodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务信息表
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowTaskInfoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4559143895248911537L;

    /**
     * 编号
     */
    private String id;

    /**
     * 流模板 ID
     */
    private String flowTemplateId;

    /**
     * 流程实例ID
     */
    private String flowInstanceId;

    /**
     * 表单 ID
     */
    private String formId;
    /**
     * 节点ID
     */
    private String nodeId;
    /**
     * 节点ID
     */
    private String nodeName;
    /**
     * 处理人ID
     */
    private String handlerId;

    /**
     * 接收时间
     */
    private LocalDateTime receivedTime;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;
    /**
     * 操作时间
     */
    private LocalDateTime approveTime;
    /**
     * 操作备注
     */
    private String approveRemarks;

    /**
     * 操作类型
     */
    private ApproveResult approveResult;

    /**
     * 操作流程顺序，
     */
    private ApproveOrder approveOrder;


    /**
     * 下一个节点ID
     */
    private String nextNodeId;

    /**
     * 任务类型
     */
    private NodeType nodeType;
    /**
     * 备注说明
     */
    private String remarks;

    /**
     * 序号
     */
    private Integer orderNumber;

    /**
     * 状态；1-待处理，2-已处理
     */
    private Integer status;

    /**
     * 乐观锁
     */
    private String revision;

    /**
     * 删除标志，0-已删除，0-未删除
     */
    private Integer delFlag;

    /**
     * 租户 ID
     */
    private String tenantId;


    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
