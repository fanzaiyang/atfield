package cn.fanzy.smart.flow.model.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流节点数据作用域
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowNodeDataScope implements Serializable {
    @Serial
    private static final long serialVersionUID = 2472835976635921220L;

    /**
     * 表单 ID
     */
    private String formId;

    /**
     * 表单键
     */
    private String formKey;

    /**
     * 编辑
     */
    private boolean edit;

    /**
     * 视图
     */
    private boolean view;
}
