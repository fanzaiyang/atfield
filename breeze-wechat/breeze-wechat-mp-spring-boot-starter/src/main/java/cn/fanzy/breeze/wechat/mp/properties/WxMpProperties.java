package cn.fanzy.breeze.wechat.mp.properties;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * wx mp属性
 *
 * @author fanzaiyang
 * @version 2021/06/02
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "breeze.wechat.mp")
public class WxMpProperties {
    /**
     * 应用程序配置集合
     */
    private List<AppConfig> appConfigs;

    @Getter
    @Setter
    public static class AppConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}