package cn.fanzy.atfield.sqltoy.property;

import cn.fanzy.atfield.sqltoy.delflag.enums.DeleteValueStrategyEnum;
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
     * 逻辑删除字段;数据库中的字段
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

    /**
     * 删除值策略,默认静态值，取logicDeleteValue字段
     */
    private DeleteValueStrategyEnum deleteValueStrategy = DeleteValueStrategyEnum.STATIC;
    /**
     * 字段类型;默认：实体类字段，需要通过注解获取对应的数据库字段
     * 推荐使用数据库字段
     */
    private FieldTypeEnum fieldType = FieldTypeEnum.ENTITY;

    /**
     * 是否驼峰转为下划线;
     * 当实体类匹配不到时，使用驼峰转下划线作为数据库字段。
     */
    private boolean humpToUnderline = true;


    /**
     * 强制更新字段;
     * 强制更新字段，当update时，只更新这些字段，其他字段不更新
     */
    private String[] forceUpdateFields;

    public static enum FieldTypeEnum {
        /**
         * 数据库字段
         */
        DATABASE,
        /**
         * 实体字段，需要通过注解获取对应的数据库字段
         */
        ENTITY
    }
}
