/**
 *
 */
package cn.fanzy.breeze.web.cache.properties;

import cn.fanzy.breeze.web.cache.enums.BreezeCacheEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 缓存属性配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Data
@ConfigurationProperties(prefix = "breeze.web.cache")
public class BreezeCacheProperties {
    /**
     * 缓存方式，默认：内存，若使用redis，需要引入redis依赖。
     */
    private BreezeCacheEnum type = BreezeCacheEnum.memory;
}
