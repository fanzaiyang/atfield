package cn.fanzy.breeze.wechat.cp.properties;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * wx cp属性,多Agent支持
 *
 * @author fanzaiyang
 * @version 2021/06/02
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "breeze.wechat.cp")
public class WxCpProperties {
    /**
     * 设置企业微信的corpId
     */
    private String corpId;

    /**
     * 应用程序配置集合
     */
    private List<AppConfig> appConfigs;

    @Getter
    @Setter
    public static class AppConfig {
        /**
         * 设置企业微信应用的AgentId
         */
        private Integer agentId;

        /**
         * 设置企业微信应用的Secret
         */
        private String secret;

        /**
         * 设置企业微信应用的token
         */
        private String token;

        /**
         * 设置企业微信应用的EncodingAESKey
         */
        private String aesKey;

    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}