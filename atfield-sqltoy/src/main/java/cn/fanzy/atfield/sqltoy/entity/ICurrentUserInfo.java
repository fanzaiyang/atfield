package cn.fanzy.atfield.sqltoy.entity;


import cn.fanzy.atfield.core.model.IOperator;

import java.io.Serializable;
import java.util.Map;

/**
 * iUser 在线信息
 * 请使用{@link IOperator}
 * @author fanzaiyang
 * @date 2024/01/09
 */
@Deprecated
public interface ICurrentUserInfo extends Serializable {
    /**
     * 获取用户 ID
     *
     * @return {@link String}
     */
    String getUserId();

    /**
     * 获取用户名
     *
     * @return {@link String}
     */
    String getUserName();

    /**
     * 额外
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> extra();
}
