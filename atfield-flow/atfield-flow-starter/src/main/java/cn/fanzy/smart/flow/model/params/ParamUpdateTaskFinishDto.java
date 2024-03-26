package cn.fanzy.smart.flow.model.params;

import cn.fanzy.smart.flow.model.enums.ApproveOrder;
import cn.fanzy.smart.flow.model.enums.ApproveResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 参数更新任务完成 DTO
 *
 * @author fanzaiyang
 * @date 2024/03/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamUpdateTaskFinishDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6515439809396629893L;

    /**
     * 节点 ID
     */
    private String taskId;


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
}
