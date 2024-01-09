package cn.fanzy.atfield.cache.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础设施缓存属性
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Data
@ConfigurationProperties(prefix = "atfield.cache")
public class CacheProperty {
    /**
     * 类型
     */
    private CacheType type = CacheType.AUTO;

    public enum CacheType {
        /**
         * 自动，redis优先
         */
        AUTO,
        /**
         * 内存
         */
        LOCAL,
        /**
         * redis
         */
        REDIS;
    }
}
