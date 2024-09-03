package cn.fanzy.atfield.sqltoy.entity;

import java.io.Serial;
import java.util.Map;

/**
 * 匿名用户在线信息
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
public class AnonymousCurrentUserInfo implements ICurrentUserInfo {
    @Serial
    private static final long serialVersionUID = 7189295010807320194L;

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
