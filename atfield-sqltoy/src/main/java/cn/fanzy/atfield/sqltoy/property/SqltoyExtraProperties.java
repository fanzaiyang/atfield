package cn.fanzy.atfield.sqltoy.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;

/**
 * SQLToy 额外属性
 *
 * @author fanzaiyang
 * @date 2024-01-16
 */
@Data
@ConfigurationProperties(prefix = "spring.sqltoy.extra")
public class SqltoyExtraProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -3478258553323527123L;

    /**
     * 逻辑删除字段
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
}
