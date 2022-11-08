package cn.fanzy.breeze.auth.jwt.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * MinIO属性配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "breeze.auth.jwt")
public class BreezeAuthJwtProperties implements Serializable {

    private static final long serialVersionUID = 111129130225136300L;
    /**
     * 是否启用，默认：false
     */
    private Boolean enable;
    /**
     * Jwt风格
     */
    private JwtMode mode = JwtMode.simple;

    public enum JwtMode {
        /**
         * Simple 模式：Token 风格替换,默认
         */
        simple,
        /**
         * Mixin 模式：混入部分逻辑
         */
        mixin,
        /**
         * Stateless 模式：服务器完全无状态
         */
        stateless,

        /**
         * 不校验过期
         */
        statelessNotCheck;

    }
}
