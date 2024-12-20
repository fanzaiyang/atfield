/*
 * 版权属于：yitter(yitter@126.com)
 * 开源地址：https://github.com/yitter/idgenerator
 */
package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.shortid.contract.IIdGenerator;
import cn.fanzy.atfield.core.shortid.contract.IdGeneratorException;
import cn.fanzy.atfield.core.shortid.contract.IdGeneratorOptions;
import cn.fanzy.atfield.core.shortid.idgen.DefaultIdGenerator;
import lombok.Getter;

/**
 * 这是一个调用的例子，默认情况下，单机集成者可以直接使用 nextId()。
 */
public class SnowIdUtils {

    @Getter
    private static IIdGenerator idGenInstance = null;

    /**
     * 设置参数，建议程序初始化时执行一次
     */
    public static void setIdGenerator(IdGeneratorOptions options) throws IdGeneratorException {
        idGenInstance = new DefaultIdGenerator(options);
    }

    /**
     * 生成新的Id
     * 调用本方法前，请确保调用了 SetIdGenerator 方法做初始化。
     *
     * @return nextId
     */
    public static long nextId() throws IdGeneratorException {
        if (idGenInstance == null) {
            idGenInstance = new DefaultIdGenerator(new IdGeneratorOptions((short) 1));
        }

        return idGenInstance.newLong();
    }
}
