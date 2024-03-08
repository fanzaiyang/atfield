package cn.fanzy.flow.model.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 流节点条件
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowNodeCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = 2472835976635921220L;

    /**
     * 数据库sql
     */
    private String sql;

    /**
     * 表单键
     */
    private Map<String, Object> sqlParam;

    /**
     * 编辑
     */
    private int result;

    /**
     * 视图
     */
    private String nextNodeId;
}
