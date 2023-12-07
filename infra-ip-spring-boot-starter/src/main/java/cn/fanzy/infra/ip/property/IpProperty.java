package cn.fanzy.infra.ip.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "infra.web.response.wrapper")
public class IpProperty {
    /**
     * 允许ips
     */
    private String[] allowedIps;
    /**
     * 拒绝ips
     */
    private String[] deniedIps;
}
