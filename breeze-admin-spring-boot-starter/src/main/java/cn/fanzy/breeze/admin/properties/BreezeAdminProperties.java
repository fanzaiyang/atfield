package cn.fanzy.breeze.admin.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.admin")
public class BreezeAdminProperties implements Serializable {
    private static final long serialVersionUID = 1706602478025805316L;
    private String appName;
    /**
     * 接口前缀
     */
    private Prefix prefix = new Prefix();
    /**
     * 模块配置
     */
    private Module module = new Module();

    private ErrorEnum errorLevel = ErrorEnum.all;

    private String defaultPassword = "123456a?";

    @Data
    public static class Module {
        /**
         * 启用授权模块。默认：true
         */
        private Boolean enableAuth;
        /**
         * 启用系统管理模块。默认：true
         */
        private Boolean enableSystem;
        /**
         * 启用系统管理-账户管理模块。默认：true
         */
        private Boolean enableAccount;
        /**
         * 启用系统管理-组织管理模块。默认：true
         */
        private Boolean enableOrg;
        /**
         * 启用系统管理-字典管理模块。默认：true
         */
        private Boolean enableDict;
        /**
         * 启用系统管理-菜单管理模块。默认：true
         */
        private Boolean enableMenu;
        /**
         * 启用系统管理-角色管理模块。默认：true
         */
        private Boolean enableRole;
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
        /**
         * 菜单管理模块前缀,默认：/sys/menu
         */
        private String menu;
        /**
         * 字典管理模块前缀,默认：/sys/dict
         */
        private String dict;
        /**
         * 组织架构模块前缀,默认：/sys/org
         */
        private String org;
    }

    public enum ErrorEnum {
        /**
         * 所有
         */
        all,
        /**
         * 仅记录错误
         */
        error
    }
}
