package cn.fanzy.atfield.leaf.property;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "leaf.id")
public class LeafIdProperty {
    /**
     * 表名称,默认：leaf_alloc
     */
    private String tableName = "leaf_alloc";
    private String tableSchema;

    public String getTableName() {
        return StrUtil.blankToDefault(tableName, "leaf_alloc");
    }
}
