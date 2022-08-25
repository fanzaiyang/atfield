package cn.fanzy.breeze.wechat.pay.config;

import cn.fanzy.breeze.wechat.pay.properties.WxPayProperties;
import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * wx支付配置
 *
 * @author fanzaiyang
 * @date 2022/03/08
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
        payConfig.setAppId(StrUtil.trimToNull(this.properties.getAppId()));
        payConfig.setMchId(StrUtil.trimToNull(this.properties.getMchId()));
        payConfig.setMchKey(StrUtil.trimToNull(this.properties.getMchKey()));
        payConfig.setSubAppId(StrUtil.trimToNull(this.properties.getSubAppId()));
        payConfig.setSubMchId(StrUtil.trimToNull(this.properties.getSubMchId()));
        payConfig.setKeyPath(StrUtil.trimToNull(this.properties.getKeyPath()));
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(this.properties.isUseSandboxEnv());
        mpServices = new WxPayServiceImpl();
        mpServices.setConfig(payConfig);
    }
}
