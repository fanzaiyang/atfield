package cn.fanzy.breeze.openapi3.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author fanzaiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.swagger")
public class BreezeSwaggerProperties implements Serializable {
    private static final long serialVersionUID = -3642562913132999859L;

    private Boolean enable;

    private List<String> packagesToScan;

}
