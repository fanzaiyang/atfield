package cn.fanzy.atfield.leaf.model;

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
    private static final long serialVersionUID = 2926875119247193577L;

    private String tableName;
}
