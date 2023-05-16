package cn.fanzy.breeze.web.response.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 响应包装属性
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.response.wrapper")
public class ResponseWrapperProperties implements Serializable {
    private static final long serialVersionUID = -3642562913132999859L;

    private Boolean enable;

}
