package cn.fanzy.atfield.leaf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 叶子分配
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeafAlloc implements Serializable {
    @Serial
    private static final long serialVersionUID = -3010288039932012558L;

    /**
     * biz 标签
     */
    private String bizTag;

    /**
     * 当前值
     */
    private long maxId;

    /**
     * 步长
     */
    private int step;

    /**
     * 言论
     */
    private String remarks;


    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
