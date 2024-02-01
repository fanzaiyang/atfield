package cn.fanzy.atfield.redis;

import cn.hutool.core.util.NumberUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * TTL Redis 缓存管理器
 *
 * @author fanzaiyang
 * @date 2024/02/01
 */
public class TtlRedisCacheManager extends RedisCacheManager {
    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
                                RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(@NonNull String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        if (array.length > 1) { // 解析TTL
            // 例如 12 默认12秒， 12d=12天
            String ttlStr = array[1];
            Duration duration = convertDuration(ttlStr);
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(name, cacheConfig);
    }

    private Duration convertDuration(String ttlStr) {
        if (NumberUtil.isLong(ttlStr)) {
            return Duration.ofSeconds(Long.parseLong(ttlStr));
        }
        return Duration.parse(ttlStr.toUpperCase());
    }
}
