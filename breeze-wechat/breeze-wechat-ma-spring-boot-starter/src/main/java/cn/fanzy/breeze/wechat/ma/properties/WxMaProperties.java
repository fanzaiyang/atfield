package cn.fanzy.breeze.wechat.ma.properties;

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
@ConfigurationProperties(prefix = "breeze.wechat.ma")
public class WxMaProperties {
    /**
     * 应用程序配置集合
     */
    private List<AppConfig> appConfigs;

    @Getter
    @Setter
    public static class AppConfig {
        /**
         * 设置微信小程序的appid.
         */
        private String appId;

        /**
         * 设置微信小程序的Secret.
         */
        private String secret;

        /**
         * 设置微信小程序消息服务器配置的token.
         */
        private String token;

        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey.
         */
        private String aesKey;

        /**
         * 消息格式，XML或者JSON.
         */
        private String msgDataFormat;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}