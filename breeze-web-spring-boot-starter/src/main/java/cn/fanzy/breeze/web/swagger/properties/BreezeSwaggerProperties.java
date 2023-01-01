package cn.fanzy.breeze.web.swagger.properties;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;

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

    /**
     * 标题
     */
    private String title = "API文档";
    /**
     * 版本
     */
    private String version = "1.0.0";

    private String description = "此为该API文档";

    private String summary = "API文档集合";

    private String termsOfService = "https://gitee.com/it-xiaofan/breeze-spring-cloud";

    private License license;

    private Contact contact;

    private Map<String, Object> extensions;

    public License getLicense() {
        if (license == null) {
            return new License()
                    .name("Apache License 2")
                    .url("https://gitee.com/it-xiaofan/breeze-spring-cloud/blob/master/LICENSE");
        }
        return license;
    }

    public Contact getContact() {
        if (contact == null) {
            return new Contact().name("小范同学")
                    .url("https://gitee.com/it-xiaofan/breeze-spring-cloud")
                    .email("zaiyangnihao@163.com");
        }
        return contact;
    }
}
