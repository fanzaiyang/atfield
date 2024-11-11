package cn.fanzy.atfield.leaf.core.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 结果
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    @Serial
    private static final long serialVersionUID = -7489428610786009956L;

    /**
     * 主键
     */
    private long id;
    /**
     * 地位
     */
    private Status status;
}
