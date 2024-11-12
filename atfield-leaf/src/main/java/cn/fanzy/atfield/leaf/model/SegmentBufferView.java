package cn.fanzy.atfield.leaf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Segment Buffer 视图
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentBufferView implements Serializable {
    @Serial
    private static final long serialVersionUID = 5614310877913818624L;
    private String key;
    private long value0;
    private int step0;
    private long max0;

    private long value1;
    private int step1;
    private long max1;
    private int pos;
    private boolean nextReady;
    private boolean initOk;
}
