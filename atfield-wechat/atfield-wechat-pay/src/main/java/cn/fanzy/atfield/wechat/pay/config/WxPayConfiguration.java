package cn.fanzy.atfield.wechat.pay.config;

import cn.fanzy.atfield.wechat.pay.properties.WxPayProperties;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * wx支付配置
 *
 * @author fanzaiyang
 * @since 2022/03/08
 */
@Slf4j
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
    private final WxPayProperties properties;
    private static WxPayService mpServices;

    public WxPayConfiguration(WxPayProperties properties) {
        this.properties = properties;
    }


    public static WxPayService getWxPayService() {
        return mpServices;
    }


    @PostConstruct
    public void init() {
        log.debug("【微信组件】: 开启 <微信支付> 相关的配置");
        if (this.properties == null) {
            throw new WxRuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(this.properties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.properties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.properties.getMchKey()));
        payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));
        //以下是apiv3以及支付分相关
        payConfig.setServiceId(StringUtils.trimToNull(this.properties.getServiceId()));
        payConfig.setPayScoreNotifyUrl(StringUtils.trimToNull(this.properties.getPayScoreNotifyUrl()));
        payConfig.setPrivateKeyPath(StringUtils.trimToNull(this.properties.getPrivateKeyPath()));
        payConfig.setPrivateCertPath(StringUtils.trimToNull(this.properties.getPrivateCertPath()));
        payConfig.setCertSerialNo(StringUtils.trimToNull(this.properties.getCertSerialNo()));
        payConfig.setApiV3Key(StringUtils.trimToNull(this.properties.getApiv3Key()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(this.properties.isUseSandboxEnv());
        mpServices = new WxPayServiceImpl();
        mpServices.setConfig(payConfig);
    }
}
