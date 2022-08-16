package cn.fanzy.breeze.web.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 「微风」模型属性配置
 *
 * @author fanzaiyang
 * @date 2022-08-16
 */
@Data
@ConfigurationProperties(prefix = "breeze.model")
public class BreezeModelProperties {
    /**
     * 成功代码，默认：200
     */
    private int successCode = 200;

    /**
     * 成功消息
     */
    private String successMessage = "操作成功！";
    /**
     * 成功代码，默认：200
     */
    private int errorCode = -100;

    /**
     * 成功消息
     */
    private String errorMessage = "操作失败！";
}
