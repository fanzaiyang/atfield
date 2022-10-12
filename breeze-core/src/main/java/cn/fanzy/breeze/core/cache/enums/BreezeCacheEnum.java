package cn.fanzy.breeze.core.cache.enums;

public enum BreezeCacheEnum {
    /**
     * redis,需要引入redis依赖
     */
    redis,
    /**
     * 内存，默认。
     */
    memory,
    /**
     * 自动选择redis或memory，有限redis
     */
    auto;
}
