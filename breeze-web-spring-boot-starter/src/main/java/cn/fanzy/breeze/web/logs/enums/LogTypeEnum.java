package cn.fanzy.breeze.web.logs.enums;

/**
 * 日志类型枚举
 *
 * @author fanzaiyang
 * @date 2023-04-23
 */
public enum LogTypeEnum {
    /**
     * 未知
     */
    NONE,

    /**
     * 登录
     */
    LOGIN,
    /**
     * 查询
     */
    QUERY,
    /**
     * 命令保存
     */
    COMMAND_SAVE,
    /**
     * 命令删除
     */
    COMMAND_DELETE;

}
