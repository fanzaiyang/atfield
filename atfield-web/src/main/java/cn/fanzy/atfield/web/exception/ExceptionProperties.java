/**
 *
 */
package cn.fanzy.atfield.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;


/**
 * 异常处理相关配置
 *
 * @author fanzaiyang
 * @since 2021/09/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "atfield.web.exception")
public class ExceptionProperties implements Serializable {
    @Serial
    private static final long serialVersionUID = -2465252437257164820L;
    /**
     * 异常返回前端是否抛出异常，默认：true-http状态码=200，false-http状态码!=200
     */
    private Boolean statusOk = true;


}
