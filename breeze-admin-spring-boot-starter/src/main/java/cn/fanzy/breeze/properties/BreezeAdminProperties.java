package cn.fanzy.breeze.properties;

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
    private String prefix = "";
}
