package cn.fanzy.atfield.sqltoy.entity;

import java.util.Map;

/**
 * 匿名用户在线信息
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
public class AnonymousCurrentUserInfo implements ICurrentUserInfo {
    @Override
    public String getUserId() {
        return "anonymous";
    }

    @Override
    public String getUserName() {
        return "anonymous";
    }

    @Override
    public Map<String, Object> extra() {
        return Map.of();
    }
}
