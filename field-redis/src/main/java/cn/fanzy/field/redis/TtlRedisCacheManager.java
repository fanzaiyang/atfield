package cn.fanzy.field.redis;

import cn.hutool.core.util.NumberUtil;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

public class TtlRedisCacheManager extends RedisCacheManager {
    public TtlRedisCacheManager(RedisCacheWriter cacheWriter,
                                RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
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
