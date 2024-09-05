package cn.fanzy.atfield.sqltoy.entity;

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
public class ParamBatchDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 4582477467794940501L;

    /**
     * 目前ID
     */
    private List<String> targets;

    /**
     * 下一状态
     */
    private Integer nextStatus;
}
