package cn.fanzy.atfield.satoken.model;

import cn.hutool.json.JSONObject;

import java.io.Serializable;
import java.util.Collection;

/**
 * 用户详细信息
 * Provides core user information.
 *
 * @author fanzaiyang
 * @date 2024/01/19
 */
public interface UserDetails extends Serializable {

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
    String getUsername();

    /**
     * 获取密码
     *
     * @return {@link String}
     */
    String getPassword();


    /**
     * 帐户是否未过期
     *
     * @return boolean
     */
    boolean isAccountNonExpired();


    /**
     * 帐户是否未锁定
     *
     * @return boolean
     */
    boolean isAccountNonLocked();

    /**
     * 凭据是否未过期
     *
     * @return boolean
     */
    boolean isCredentialsNonExpired();


    /**
     * 已启用
     *
     * @return boolean
     */
    boolean isEnabled();

    /**
     * 获得额外
     *
     * @return {@link JSONObject}
     */
    JSONObject getExtra();

    /**
     * 获取权限
     *
     * @return {@link Collection}<{@link ?} {@link extends} {@link String}>
     */
    Collection<? extends String> getAuthorities();
}
