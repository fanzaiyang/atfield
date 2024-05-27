package cn.fanzy.atfield.wechat.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * wx支付属性
 *
 * @author fanzaiyang
 * @since 2022/03/08
 */
@Data
@ConfigurationProperties(prefix = "atfield.wechat.pay")
public class WxPayProperties {
    /**
     * 设置微信公众号或者小程序等的appid.
     */
    private String appId;

    /**
     * 微信支付商户号.
     */
    private String mchId;

    /**
     * 微信支付商户密钥.
     */
    private String mchKey;

    /**
     * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除.
     */
    private String subAppId;

    /**
     * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除.
     */
    private String subMchId;

    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定.
     */
    private String keyPath;

    /**
     * 微信支付分serviceId
     */
    private String serviceId;

    /**
     * 证书序列号
     */
    private String certSerialNo;

    /**
     * apiV3秘钥
     */
    private String apiv3Key;

    /**
     * 微信支付分回调地址
     */
    private String payScoreNotifyUrl;

    /**
     * apiv3 商户apiclient_key.pem
     */
    private String privateKeyPath;

    /**
     * apiv3 商户apiclient_cert.pem
     */
    private String privateCertPath;

    private boolean useSandboxEnv;
}