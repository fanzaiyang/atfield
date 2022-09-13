package cn.fanzy.breeze.wechat.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * wx支付属性
 *
 * @author fanzaiyang
 * @date 2022/03/08
 */
@Data
@ConfigurationProperties(prefix = "breeze.wechat.pay")
public class WxPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 微信支付商户密钥
     */
    private String mchKey;

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    private String subMchId;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    /**
     * 以指定是否使用沙箱环境,默认false
     */
    private boolean useSandboxEnv = false;
}