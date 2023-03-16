package cn.fanzy.breeze.sqltoy.properties;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author fanzaiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.sqltoy")
public class BreezeSqlToyProperties implements Serializable {
    private static final long serialVersionUID = -3642562913132999859L;

    private boolean skip;
    /**
     * 别名，用于负责查询中列的别名.
     */
    private String alias;
    /**
     * 逻辑删除的数据库字段
     */
    private String logicDeleteField;

    /**
     * 逻辑删除值
     */
    private String logicDeleteValue;
    /**
     * 逻辑不删除值
     */
    private String logicNotDeleteValue;

    public String getAlias() {
        if (StrUtil.isBlank(alias)) {
            return "";
        }
        return alias.concat(".");
    }
}
