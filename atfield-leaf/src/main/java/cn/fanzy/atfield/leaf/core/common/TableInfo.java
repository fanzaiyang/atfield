package cn.fanzy.atfield.leaf.core.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 表信息
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1033632013490785716L;

    /**
     * 表名称
     */
    private String tableName;
}
