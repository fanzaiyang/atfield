package cn.fanzy.breeze.admin.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.admin")
public class BreezeAdminProperties {
    /**
     * 接口前缀
     */
    private Prefix prefix = new Prefix();
    /**
     * 模块配置
     */
    private Module module = new Module();

    @Data
    public static class Module {
        private Boolean enableAuth = true;
    }

    @Data
    public static class Prefix {
        /**
         * API接口统一前缀
         */
        private String api;
        /**
         * 授权登录模块前缀,默认：/auth
         */
        private String auth;
        /**
         * 账户管理模块前缀,默认：/sys/account
         */
        private String account;
        /**
         * 角色管理模块前缀,默认：/sys/role
         */
        private String role;
    }
}
