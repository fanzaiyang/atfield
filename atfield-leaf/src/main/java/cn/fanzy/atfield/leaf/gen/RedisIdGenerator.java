package cn.fanzy.atfield.leaf.gen;

/**
 * 主键发电机
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
public interface RedisIdGenerator {

    /**
     * 下一个主键
     *
     * @param key 钥匙
     * @return long
     */
    long nextId(String key);

    /**
     * 以前主键
     *
     * @param key 钥匙
     * @return long
     */
    long previousId(String key);

    /**
     * 初始化
     */
    void init();
}
