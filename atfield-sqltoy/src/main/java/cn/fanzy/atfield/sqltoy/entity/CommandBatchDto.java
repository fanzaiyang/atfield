package cn.fanzy.atfield.sqltoy.entity;

import cn.hutool.core.collection.CollUtil;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 参数批DTO
 *
 * @author fanzaiyang
 * @date 2024-07-01
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandBatchDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 4582477467794940501L;

    /**
     * 目标ID集合
     */
    @NotEmpty(message = "目标ID集合不能为空！")
    private List<String> targets;
    /**
     * 目标ID
     */
    private List<String> targetIds;
    /**
     * 下一值;不限制类型，由调用方自行处理
     * 常用：1-启用/激活/删除，0-禁用/失效/未删除
     */
    private Object nextValue;

    public List<String> getTargets() {
        if (CollUtil.isNotEmpty(targets)) {
            return targets;
        }
        return targetIds;
    }

    public List<String> getTargetIds() {
        if (CollUtil.isNotEmpty(targetIds)) {
            return targetIds;
        }
        return targets;
    }
}
