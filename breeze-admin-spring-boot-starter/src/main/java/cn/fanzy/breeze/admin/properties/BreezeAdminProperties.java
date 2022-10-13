package cn.fanzy.breeze.admin.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.admin")
public class BreezeAdminProperties {
    /**
     * 接口前缀
     */
    private String prefix = "/auth";
    /**
     * 模块配置
     */
    private Module module = new Module();

    @Data
    public static class Module {
        private Boolean enableAuth = true;
    }
}
