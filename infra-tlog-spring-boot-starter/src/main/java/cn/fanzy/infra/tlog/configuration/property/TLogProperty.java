package cn.fanzy.infra.tlog.configuration.property;

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

    /**
     * 图案
     */
    private String pattern;

    /**
     * id生成器
     */
    private String idGenerator;

    /**
     * 启用mdc
     */
    private Boolean mdcEnable;

    /**
     * 启用调用时间打印
     */
    private boolean enableInvokeTimePrint;

    /**
     * 打印配置
     */
    private PrintInfo print = new PrintInfo();

    private Boolean ignoreSpringdoc=true;
    @Data
    public static class PrintInfo {
        private Boolean preEnable = true;
        /**
         * 前置格式
         */
        private String prePattern = "[TLog]=>客户端[$clientIp],用户标识[$userId]，请求地址[$requestUrl],请求参数：$requestData";

        private Boolean afterEnable = true;

        /**
         * 后置格式
         */
        private String afterPattern = "[TLog]=>响应耗时[$spendMillis]ms,结果：$responseData";

        /**
         * 响应数据长度,超过该长度则打印省略号
         * -1-不限制
         * 0-不打印
         */
        private int responseDataLength = 1024;
    }


}
