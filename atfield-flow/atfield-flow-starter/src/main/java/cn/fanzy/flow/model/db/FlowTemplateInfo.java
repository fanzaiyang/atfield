package cn.fanzy.flow.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流模板信息
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowTemplateInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4559143895248911537L;

    /**
     * 编号
     */
    private String id;

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 短名称
     */
    private String shortName;

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
     * 流程版本号
     */
    private Integer flowVersion;

    /**
     * 流量数据JSON ARRAY
     */
    private String flowData;

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
