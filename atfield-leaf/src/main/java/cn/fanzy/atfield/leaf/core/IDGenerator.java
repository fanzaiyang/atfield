package cn.fanzy.atfield.leaf.core;


import cn.fanzy.atfield.leaf.core.common.Result;

/**
 * idgen
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
public interface IDGenerator {
    /**
     * 获取
     *
     * @param key 钥匙
     * @return {@link Result }
     */
    Result get(String key);

    /**
     * 初始化
     *
     * @return boolean
     */
    boolean init();
}
