package cn.fanzy.breeze.web.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 「微风」模型属性配置
 *
 * @author fanzaiyang
 * @version 2022-08-16
 */
@Data
@ConfigurationProperties(prefix = "breeze.web.model")
public class BreezeModelProperties implements Serializable {
    private static final long serialVersionUID = 3199356034904954698L;
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
