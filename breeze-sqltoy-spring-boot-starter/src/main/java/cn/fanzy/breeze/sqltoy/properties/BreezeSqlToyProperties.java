package cn.fanzy.breeze.sqltoy.properties;

import cn.fanzy.breeze.sqltoy.enums.LogicalDeleteEnum;
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

    private Boolean skipSqlMode;
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
     * 已删除值生成策略
     */
    private LogicalDeleteEnum deleteValueStrategy;
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

    public Boolean getSkipSqlMode() {
        return skipSqlMode == null || skipSqlMode;
    }

    public LogicalDeleteEnum getDeleteValueStrategy() {
        if (deleteValueStrategy == null) {
            return LogicalDeleteEnum.value;
        }
        return deleteValueStrategy;
    }
}
