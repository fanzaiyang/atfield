package cn.fanzy.smart.flow.model.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

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
public class FlowNodeHandler implements Serializable {
    @Serial
    private static final long serialVersionUID = 2472835976635921220L;

    private String id;

    private String name;

    private String sql;
    /**
     * 表单键
     */
    private Map<String, Object> sqlParam;
}
