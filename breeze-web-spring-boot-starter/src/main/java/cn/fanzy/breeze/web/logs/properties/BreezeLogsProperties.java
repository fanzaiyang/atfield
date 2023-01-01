package cn.fanzy.breeze.web.logs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author fanzaiyang
 */
@Data
@ConfigurationProperties(prefix = "breeze.web.log")
public class BreezeLogsProperties implements Serializable {
    private static final long serialVersionUID = 8967687994537766398L;
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
