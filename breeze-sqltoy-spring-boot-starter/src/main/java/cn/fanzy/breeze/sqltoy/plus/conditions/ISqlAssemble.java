package cn.fanzy.breeze.sqltoy.plus.conditions;

/**
 * 标记为可组装sql接口
 */
public interface ISqlAssemble {

    /**
     * 组装器
     * @return
     */
    ISqlAssembler assembler();
}
