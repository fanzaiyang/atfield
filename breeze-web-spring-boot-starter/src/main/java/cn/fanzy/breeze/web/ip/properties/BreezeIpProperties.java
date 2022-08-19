package cn.fanzy.breeze.web.ip.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微风ip属性
 *
 * @author fanzaiyang
 * @date 2022-08-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.ip")
public class BreezeIpProperties {
    /**
     * 是否开启配置，默认：False
     */
    private boolean enable;
    /**
     * 允许的IP
     */
    private String[] allowed;

    /**
     * 拒绝访问的IP
     */
    private String[] deny;

    /**
     * 拦截的路径，多个逗号隔开。默认拦截所有/**
     */
    private String[] pathPatterns;

}
