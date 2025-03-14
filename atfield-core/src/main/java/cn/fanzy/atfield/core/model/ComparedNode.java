package cn.fanzy.atfield.core.model;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
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
        return StrUtil.format("<strong>{}</strong> 由 <em class=\"sv\">{}<em> 变更为 <em  class=\"tv\">{}</em>",
                getFieldName(),
                ObjUtil.isNull(getFieldValue()) ? "空" : getFieldValue(),
                ObjUtil.isNull(getNewFieldValue()) ? "空" : getNewFieldValue()
        );
    }

    public String getText() {
        return StrUtil.format("{} 由 {} 变更为 {}",
                getFieldName(),
                ObjUtil.isNull(getFieldValue()) ? "空" : getFieldValue(),
                ObjUtil.isNull(getNewFieldValue()) ? "空" : getNewFieldValue()
        );
    }
}
