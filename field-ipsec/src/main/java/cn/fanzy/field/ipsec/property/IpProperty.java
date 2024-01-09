package cn.fanzy.field.ipsec.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "at.field.web.ip")
public class IpProperty {
    /**
     * 允许ips
     */
    private String[] allowedIps = new String[]{};
    /**
     * 拒绝ips
     */
    private String[] deniedIps = new String[]{};

    private GlobalConfig global = new GlobalConfig();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GlobalConfig {
        /**
         * 是否启用，默认：False
         */
        private Boolean enable;
        /**
         * 排除路径模式,默认无
         */
        private String[] excludePathPatterns;
        /**
         * 包括路径模式,默认：/**
         */
        private String[] includePathPatterns = new String[]{"/**"};
    }
}
