package cn.fanzy.breeze.web.web.json.properties;

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
@ConfigurationProperties(prefix = "breeze.web.json")
public class BreezeWebJsonProperties implements Serializable {
    private static final long serialVersionUID = -3642562913132999859L;

    private Boolean enable;
}
