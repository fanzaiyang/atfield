package cn.fanzy.atfield.leaf.core.segment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 叶子分配
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeafAlloc implements Serializable {
    @Serial
    private static final long serialVersionUID = -7787713145028519281L;
    /**
     * 钥匙
     */
    private String key;

    /**
     * biz 标签
     */
    private String bizTag;
    /**
     * 麦克斯主键
     */
    private long maxId;
    /**
     * 步
     */
    private int step;
    /**
     * 更新时间
     */
    private String updateTime;

}
