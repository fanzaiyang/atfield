package cn.fanzy.smart.flow.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程实例
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowInstanceInfoEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4559143895248911537L;

    /**
     * 编号
     */
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 流程主题
     */
    private String title;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 流模板 ID
     */
    private String flowTemplateId;

    /**
     * 流程状态；0-草稿，1-进行中，2-已完成，3-废弃，4-异常
     */
    private Integer flowStatus;

    /**
     * 申请人ID
     */
    private String applyUserId;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 当前节点 ID
     */
    private String currentNodeId;

    /**
     * 当前节点名称
     */
    private String currentNodeName;

    /**
     * 当前处理人
     */
    private String currentHandlerIds;

    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;

    /**
     * 下一个节点 ID
     */
    private String nextNodeId;

    /**
     * 下一个节点名称
     */
    private String nextNodeName;

    /**
     * 下一个处理人
     */
    private String nextHandlerIds;

    /**
     * 流量数据
     */
    private String flowData;
    /**
     * 备注说明
     */
    private String remarks;

    /**
     * 序号
     */
    private Integer orderNumber;

    /**
     * 状态
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
