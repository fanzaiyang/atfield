package cn.fanzy.breeze.wechat.ma;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.fanzy.breeze.wechat.ma.handler.*;
import cn.fanzy.breeze.wechat.ma.properties.WxMaProperties;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * wx cp配置
 *
 * @author fanzaiyang
 * @since 2021/09/09
 */
@Slf4j
@Configuration
@Import({WxMaBeanAutoConfig.class})
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {
    private final WxMaProperties properties;
    public static List<WxMaProperties.AppConfig> appConfigList;
    private static final Map<String, WxMaMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMaService> maServices;
    private final WxMaImageHandler wxMaImageHandler;
    private final WxMaLogHandler wxMaLogHandler;
    private final WxMaQrCodeHandler wxMaQrCodeHandler;
    private final WxMaSubscribeHandler wxMaSubscribeHandler;
    private final WxMaTextHandler wxMaTextHandler;

    public WxMaConfiguration(WxMaProperties properties, WxMaImageHandler wxMaImageHandler, WxMaLogHandler wxMaLogHandler, WxMaQrCodeHandler wxMaQrCodeHandler, WxMaSubscribeHandler wxMaSubscribeHandler, WxMaTextHandler wxMaTextHandler) {
        this.properties = properties;
        this.wxMaImageHandler = wxMaImageHandler;
        this.wxMaLogHandler = wxMaLogHandler;
        this.wxMaQrCodeHandler = wxMaQrCodeHandler;
        this.wxMaSubscribeHandler = wxMaSubscribeHandler;
        this.wxMaTextHandler = wxMaTextHandler;
    }

    /**
     * 默认获取第一个配置Agent应用的服务
     *
     * @return {@link WxMaService}
     */
    public static WxMaService getMaService() {
        if (CollUtil.isEmpty(appConfigList)) {
            return null;
        }
        return getMaService(appConfigList.get(0).getAppId());
    }

    public static WxMaService getMaService(String appid) {
        WxMaService wxService = maServices.get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        return wxService;
    }

    public static WxMaMessageRouter getRouter(String appid) {
        return routers.get(appid);
    }

    @PostConstruct
    public void init() {
        log.debug("【微信组件】: 开启 <微信小程序> 相关的配置");
        List<WxMaProperties.AppConfig> configs = this.properties.getAppConfigs();
        if (configs == null) {
            throw new WxRuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        appConfigList = this.properties.getAppConfigs();
        maServices = configs.stream()
                .map(a -> {
                    WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
//                WxMaDefaultConfigImpl config = new WxMaRedisConfigImpl(new JedisPool());
                    // 使用上面的配置时，需要同时引入jedis-lock的依赖，否则会报类无法找到的异常
                    config.setAppid(a.getAppId());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    config.setMsgDataFormat(a.getMsgDataFormat());

                    WxMaService service = new WxMaServiceImpl();
                    service.setWxMaConfig(config);
                    routers.put(a.getAppId(), this.newRouter(service));
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxMaConfig().getAppid(), a -> a));
    }

    private WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router
                .rule().handler(wxMaLogHandler).next()
                .rule().async(false).content("订阅消息").handler(wxMaSubscribeHandler).end()
                .rule().async(false).content("文本").handler(wxMaTextHandler).end()
                .rule().async(false).content("图片").handler(wxMaImageHandler).end()
                .rule().async(false).content("二维码").handler(wxMaQrCodeHandler).end();
        return router;
    }
}
