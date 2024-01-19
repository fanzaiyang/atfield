package cn.fanzy.atfield.redis.enums;

/**
 * 防止重复提交类型枚举
 *
 * @author fanzaiyang
 * @date 2023/09/07
 */
public enum FormSubmitType {
    /**
     * 基于IP
     */
    IP,
    /**
     * 基于参数
     */
    PARAM,
    /**
     * 基于IP和参数,即：同一个IP，同样请求参数不允许重复提交。
     */
    IP_AND_PARAM
}
