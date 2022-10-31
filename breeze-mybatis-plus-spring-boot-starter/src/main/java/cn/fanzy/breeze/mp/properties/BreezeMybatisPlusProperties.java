/**
 *
 */
package cn.fanzy.breeze.mp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;


/**
 * 跨域支持属性配置
 *
 * @author fanzaiyang
 * @date 2021/09/06
 */
@Data
@ConfigurationProperties(prefix = "mybatis-plus.global-config")
public class BreezeMybatisPlusProperties implements Serializable {
    private static final long serialVersionUID = 6485346584325150363L;
    /**
     * 乐观锁开关，默认开启
     */
    private Boolean optimisticLocker = true;

    /**
     * 全表删除
     */
    private Boolean blockAttack = true;

}
