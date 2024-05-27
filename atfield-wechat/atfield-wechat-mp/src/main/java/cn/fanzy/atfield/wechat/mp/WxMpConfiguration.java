package cn.fanzy.atfield.wechat.mp;

import cn.fanzy.atfield.wechat.mp.handler.*;
import cn.fanzy.atfield.wechat.mp.properties.WxMpProperties;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.*;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.POI_CHECK_NOTIFY;

/**
 * wx cp配置
 *
 * @author fanzaiyang
 * @since 2021/09/09
 */
@Slf4j
@Configuration
@Import({WxMpBeanAutoConfig.class})
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    private final WxMpProperties properties;
    public static List<WxMpProperties.AppConfig> appConfigList;

    private static final Map<String, WxMpMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxMpService> mpServices;

    private final WxMpKfSessionHandler wxMpKfSessionHandler;
    private final WxMpLocationHandler wxMpLocationHandler;
    private final WxMpLogHandler wxMpLogHandler;
    private final WxMpMenuHandler wxMpMenuHandler;
    private final WxMpMsgHandler wxMpMsgHandler;
    private final WxMpNullHandler wxMpNullHandler;
    private final WxMpScanHandler wxMpScanHandler;
    private final WxMpStoreCheckNotifyHandler wxMpStoreCheckNotifyHandler;
    private final WxMpSubscribeHandler wxMpSubscribeHandler;
    private final WxMpUnsubscribeHandler wxMpUnsubscribeHandler;

    public WxMpConfiguration(WxMpProperties properties, WxMpKfSessionHandler wxMpKfSessionHandler, WxMpLocationHandler wxMpLocationHandler, WxMpLogHandler wxMpLogHandler, WxMpMenuHandler wxMpMenuHandler, WxMpMsgHandler wxMpMsgHandler, WxMpNullHandler wxMpNullHandler, WxMpScanHandler wxMpScanHandler, WxMpStoreCheckNotifyHandler wxMpStoreCheckNotifyHandler, WxMpSubscribeHandler wxMpSubscribeHandler, WxMpUnsubscribeHandler wxMpUnsubscribeHandler) {
        this.properties = properties;
        this.wxMpKfSessionHandler = wxMpKfSessionHandler;
        this.wxMpLocationHandler = wxMpLocationHandler;
        this.wxMpLogHandler = wxMpLogHandler;
        this.wxMpMenuHandler = wxMpMenuHandler;
        this.wxMpMsgHandler = wxMpMsgHandler;
        this.wxMpNullHandler = wxMpNullHandler;
        this.wxMpScanHandler = wxMpScanHandler;
        this.wxMpStoreCheckNotifyHandler = wxMpStoreCheckNotifyHandler;
        this.wxMpSubscribeHandler = wxMpSubscribeHandler;
        this.wxMpUnsubscribeHandler = wxMpUnsubscribeHandler;
    }

    /**
     * 默认获取第一个配置Agent应用的服务
     *
     * @return {@link WxMpService}
     */
    public static WxMpService getMpService() {
        if (CollUtil.isEmpty(appConfigList)) {
            return null;
        }
        return getMpService(appConfigList.get(0).getAppId());
    }

    public static WxMpService getMpService(String appid) {
        if(StrUtil.isBlank(appid)){
            return getMpService();
        }
        WxMpService wxService = mpServices.get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        return wxService;
    }

    public static WxMpMessageRouter getRouter(String appId) {
        return routers.get(appId);
    }

    @PostConstruct
    public void init() {
        log.debug("【微信组件】: 开启 <微信小程序> 相关的配置");
        List<WxMpProperties.AppConfig> configs = this.properties.getAppConfigs();
        if (configs == null) {
            throw new WxRuntimeException("【微信组件】: 请在配置文件中添加微信相关配置！参数以atfield.wechat开头！");
        }
        appConfigList = this.properties.getAppConfigs();
        mpServices = configs.stream()
                .map(a -> {
                    WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
                    config.setAppId(a.getAppId());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    WxMpService service = new WxMpServiceImpl();
                    service.setWxMpConfigStorage(config);
                    routers.put(a.getAppId(), this.newRouter(service));
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxMpConfigStorage().getAppId(), a -> a));
    }

    private WxMpMessageRouter newRouter(WxMpService service) {
        final WxMpMessageRouter router = new WxMpMessageRouter(service);
        // 记录所有事件的日志 （异步执行）
        router.rule().handler(this.wxMpLogHandler).next();

        // 接收客服会话管理事件
        router.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION)
                .handler(this.wxMpKfSessionHandler).end();
        router.rule().async(false).msgType(EVENT).event(KF_CLOSE_SESSION)
                .handler(this.wxMpKfSessionHandler).end();
        router.rule().async(false).msgType(EVENT).event(KF_SWITCH_SESSION)
                .handler(this.wxMpKfSessionHandler).end();

        // 门店审核事件
        router.rule().async(false).msgType(EVENT).event(POI_CHECK_NOTIFY).handler(this.wxMpStoreCheckNotifyHandler).end();

        // 自定义菜单事件
        router.rule().async(false).msgType(EVENT).event(EventType.CLICK).handler(this.wxMpMenuHandler).end();

        // 点击菜单连接事件
        router.rule().async(false).msgType(EVENT).event(EventType.VIEW).handler(this.wxMpNullHandler).end();

        // 关注事件
        router.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.wxMpSubscribeHandler).end();

        // 取消关注事件
        router.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.wxMpUnsubscribeHandler).end();

        // 上报地理位置事件
        router.rule().async(false).msgType(EVENT).event(EventType.LOCATION).handler(this.wxMpLocationHandler).end();

        // 接收地理位置消息
        router.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.wxMpLocationHandler).end();

        // 扫码事件
        router.rule().async(false).msgType(EVENT).event(EventType.SCAN).handler(this.wxMpScanHandler).end();

        return router;
    }
}
