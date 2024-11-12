package cn.fanzy.atfield.leaf.property;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "leaf.id")
public class LeafIdProperty {
    /**
     * 缓存前缀
     */
    private String cachePrefix = "__LEAF_ID:";
    /**
     * 表名称,默认：leaf_alloc
     */
    private String tableName = "leaf_alloc";
    /**
     * Table Schema 表 Schema
     */
    private String tableSchema;

    public String getTableName() {
        return StrUtil.blankToDefault(tableName, "leaf_alloc");
    }

    public String getCachePrefix() {
        return StrUtil.blankToDefault(cachePrefix, "__LEAF_ID:");
    }
}
