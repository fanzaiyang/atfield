package cn.fanzy.atfield.core.model;

import cn.fanzy.atfield.core.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComparedNode implements Serializable {
    @Serial
    private static final long serialVersionUID = 384617749568767149L;

    /**
     * 字段
     */
    private String fieldKey;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段值
     */
    private Object fieldValue;

    /**
     * 新字段名称
     */
    private Object newFieldValue;

    public String getHtml() {
        return String.format("<strong>{}</strong> 由 <em class=\"sv\">{}<em> 变更为 <em  class=\"tv\">{}</em>",
                fieldName, ObjectUtils.isEmpty(fieldValue) ? "空" : fieldValue, newFieldValue);
    }

    public String getText() {
        return String.format("{} 由 {} 变更为 {}",
                fieldName, ObjectUtils.isEmpty(fieldValue) ? "空" : fieldValue, newFieldValue);
    }
}
