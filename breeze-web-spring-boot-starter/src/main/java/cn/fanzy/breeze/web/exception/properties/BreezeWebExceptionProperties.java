package cn.fanzy.breeze.web.exception.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.exception")
public class BreezeWebExceptionProperties {
    /**
     * 是否启用，默认：true启用
     */
    private Boolean enable;

    /**
     * 是否基本错误拦截，默认：true启用
     */
    private Boolean enableBasicError;
}
