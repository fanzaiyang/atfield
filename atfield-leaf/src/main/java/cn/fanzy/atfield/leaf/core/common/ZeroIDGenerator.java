package cn.fanzy.atfield.leaf.core.common;


import cn.fanzy.atfield.leaf.core.IDGenerator;

/**
 * 零 IDGEN
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
public class ZeroIDGenerator implements IDGenerator {
    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}
