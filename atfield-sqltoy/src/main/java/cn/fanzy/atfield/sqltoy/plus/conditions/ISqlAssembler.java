package cn.fanzy.atfield.sqltoy.plus.conditions;


import cn.fanzy.atfield.sqltoy.plus.conditions.segments.FiledMappingStrategy;

/**
 * sql组装器
 */
public interface ISqlAssembler {

    /**
     * 组装
     * @param mappingStrategy
     *                     -字段映射策略
     */
    void assemble(FiledMappingStrategy mappingStrategy);

    /**
     * 组装使用默认字段映射策略
     */
    default void assemble() {
        assemble(FiledMappingStrategy.DefaultFiledMappingStrategy.getInstance());
    }
}
