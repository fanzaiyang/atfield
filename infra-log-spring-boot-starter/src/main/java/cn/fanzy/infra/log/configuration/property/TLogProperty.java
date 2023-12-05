package cn.fanzy.infra.log.configuration.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * tlog的springboot配置参数
 *
 * @author Bryan.Zhang
 * @since 1.1.0
 */
@Data
@ConfigurationProperties(prefix = "infra.log", ignoreUnknownFields = true)
public class TLogProperty {

    private String pattern;

    private boolean enableInvokeTimePrint;

    private String idGenerator;

    private Boolean mdcEnable;

    /**
     * 打印格式
     */
    private String printPattern;

}
