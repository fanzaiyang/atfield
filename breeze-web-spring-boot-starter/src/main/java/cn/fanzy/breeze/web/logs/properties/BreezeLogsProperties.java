package cn.fanzy.breeze.web.logs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "breeze.web.log")
public class BreezeLogsProperties {
    /**
     * 是否启用打印LOG，默认开启。
     */
    private Boolean enable = true;

    /**
     * 忽略swagger的请求
     */
    private Boolean ignoreSwagger = true;

    public Boolean getIgnoreSwagger() {
        return ignoreSwagger == null || ignoreSwagger;
    }
}
