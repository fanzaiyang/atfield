/**
 *
 */
package cn.fanzy.breeze.core.cache.properties;

import cn.fanzy.breeze.core.cache.enums.BreezeCacheEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * 缓存属性配置
 *
 * @author fanzaiyang
 * @version 2021/09/07
 */
@Data
@ConfigurationProperties(prefix = "breeze.web.cache")
public class BreezeCacheProperties implements Serializable {
    private static final long serialVersionUID = 6185203754603519343L;
    /**
     * 缓存方式，默认：自动选择
     */
    private BreezeCacheEnum type = BreezeCacheEnum.auto;
}
