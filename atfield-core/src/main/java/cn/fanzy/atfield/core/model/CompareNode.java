package cn.fanzy.atfield.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 比较节点
 *
 * @author Administrator
 * @date 2024/10/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompareNode implements Serializable {
    @Serial
    private static final long serialVersionUID = 384617749568767149L;

    /**
     * 字段
     */
    private String fieldKey;

    /**
     * 字段值
     */
    private Object fieldValue;

    /**
     * 字段名称
     */
    private String fieldName;
}
