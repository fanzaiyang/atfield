package cn.fanzy.atfield.sqltoy.delflag.enums;

import cn.fanzy.atfield.sqltoy.delflag.service.DeleteValueGenerateService;

/**
 * 逻辑删除值策略枚举
 *
 * @author fanzaiyang
 * @date 2025/01/20
 */
public enum DeleteValueStrategyEnum {
    /**
     * 静态值
     */
    STATIC,
    UUID,
    UUID_SIMPLE,

    /**
     * 雪花
     */
    SNOWFLAKE,
    /**
     * 15位Snowflake
     */
    SNOWFLAKE_SIMPLE,

    /**
     * 自定义；需要提供具体实现接口{@link DeleteValueGenerateService}
     */
    CUSTOM;
}
