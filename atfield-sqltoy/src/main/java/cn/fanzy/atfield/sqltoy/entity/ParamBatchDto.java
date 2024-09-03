package cn.fanzy.atfield.sqltoy.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamBatchDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 4582477467794940501L;

    @NotEmpty(message = "参数不能为空!")
    private List<String> targets;
    @NotNull(message = "下一状态不能为空!")
    private Integer nextStatus;
}
