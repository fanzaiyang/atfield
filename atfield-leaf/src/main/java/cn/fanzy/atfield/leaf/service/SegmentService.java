package cn.fanzy.atfield.leaf.service;

import cn.fanzy.atfield.leaf.gen.RedisIdGenerator;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SegmentService {

    private final RedisIdGenerator redisIdGenerator;

    public SegmentService(RedisIdGenerator redisIdGenerator) {
        this.redisIdGenerator = redisIdGenerator;
        redisIdGenerator.init();
    }

    /**
     * 下一个主键
     * <pre>
     *     ID+1
     * </pre>
     *
     * @param key 钥匙
     * @return long
     */
    public long nextId(String key) {
        return redisIdGenerator.nextId(key);
    }

    /**
     * 以前主键
     * <pre>
     *     现有的ID-1
     * </pre>
     *
     * @param key 钥匙
     * @return long
     */
    public long previousId(String key) {
        return redisIdGenerator.previousId(key);
    }

    /**
     * 下一个主键
     *
     * @param key    钥匙
     * @param length 长度
     * @return {@link String }
     */
    public String nextId(String key, int length) {
        long nextId = nextId(key);
        return StrUtil.padPre(String.valueOf(nextId), length, "0");
    }

    public String nextId(String key, int length, String prefix) {
        long nextId = nextId(key);
        return prefix + StrUtil.padPre(String.valueOf(nextId), length, "0");
    }


    /**
     * 以前主键
     *
     * @param key    钥匙
     * @param length 长度
     * @return {@link String }
     */
    public String previousId(String key, int length) {
        long previousId = previousId(key);
        return StrUtil.padPre(String.valueOf(previousId), length, "0");
    }

    public String previousId(String key, int length, String prefix) {
        long previousId = previousId(key);
        return prefix + StrUtil.padPre(String.valueOf(previousId), length, "0");
    }
}
