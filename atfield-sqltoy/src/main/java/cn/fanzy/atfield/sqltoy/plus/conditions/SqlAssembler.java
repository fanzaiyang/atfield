package cn.fanzy.atfield.sqltoy.plus.conditions;


import cn.fanzy.atfield.sqltoy.plus.conditions.segments.FiledMappingStrategy;

/**
 * 组装器
 */
public abstract class SqlAssembler implements ISqlAssembler{

    public ISqlAssembler formSqlAssembler;

    public SqlAssembler(ISqlAssembler formSqlAssembler) {
        this.formSqlAssembler = formSqlAssembler;
    }

    /**
     * 组装
     */
    public abstract void assemble(FiledMappingStrategy mappingStrategy);
}
