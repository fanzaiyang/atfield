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
     * @param tag 钥匙
     * @return long
     */
    long nextId(String tag);

    /**
     * 以前主键
     *
     * @param tag 钥匙
     * @return long
     */
    long previousId(String tag);

    /**
     * 初始化
     */
    void init();

    /**
     * 关闭
     */
    void close();
}
