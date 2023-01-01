package cn.fanzy.breeze.web.exception.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author fanzaiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.exception")
public class BreezeWebExceptionProperties implements Serializable {
    private static final long serialVersionUID = 568189521091678244L;
    /**
     * 是否启用，默认：true启用
     */
    private Boolean enable;

    /**
     * 是否替换SpringBoot的BasicError，默认：true启用
     */
    private Boolean replaceBasicError;

    /**
     * 返回错误堆栈
     */
    private Boolean returnErrorStack = true;
    /**
     * 返回错误堆栈长度
     */
    private Integer returnErrorStackLength = 512;

    public Integer getReturnErrorStackLength() {
        return returnErrorStackLength == null ? 512 : returnErrorStackLength;
    }
}
