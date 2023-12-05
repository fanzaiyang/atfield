package cn.fanzy.infra.cache.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础设施缓存属性
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Data
@ConfigurationProperties(prefix = "infra.cache")
public class CacheProperty {
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
