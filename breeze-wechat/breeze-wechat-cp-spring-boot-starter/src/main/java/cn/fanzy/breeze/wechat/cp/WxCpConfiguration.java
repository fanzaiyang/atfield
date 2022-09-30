package cn.fanzy.breeze.wechat.cp;

import cn.fanzy.breeze.wechat.cp.handler.*;
import cn.fanzy.breeze.wechat.cp.properties.WxCpProperties;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2021/09/09
 */
@Slf4j
@Configuration
@Import({WxCpBeanAutoConfig.class})
@EnableConfigurationProperties(WxCpProperties.class)
public class WxCpConfiguration {
    private final WxCpLogHandler wxCpLogHandler;
    private final WxCpNullHandler wxCpNullHandler;
    private final WxCpLocationHandler wxCpLocationHandler;
    private final WxCpMenuHandler wxCpMenuHandler;
    private final WxCpMsgHandler wxCpMsgHandler;
    private final WxCpUnsubscribeHandler wxCpUnsubscribeHandler;
    private final WxCpSubscribeHandler wxCpSubscribeHandler;
    private final WxCpContactChangeHandler wxCpContactChangeHandler;
    private final WxCpEnterAgentHandler wxCpEnterAgentHandler;
    private final WxCpMenuClickHandler wxCpMenuClickHandler;
    private final WxCpScanHandler wxCpScanHandler;
    private final WxCpProperties properties;
    public static List<WxCpProperties.AppConfig> appConfigList;
    private static Map<Integer, WxCpMessageRouter> routers = Maps.newLinkedHashMap();
    private static Map<Integer, WxCpService> cpServices = Maps.newLinkedHashMap();

    @Autowired
    public WxCpConfiguration(WxCpLogHandler wxCpLogHandler, WxCpNullHandler wxCpNullHandler, WxCpLocationHandler wxCpLocationHandler,
                             WxCpMenuHandler wxCpMenuHandler, WxCpMsgHandler wxCpMsgHandler, WxCpUnsubscribeHandler wxCpUnsubscribeHandler,
                             WxCpSubscribeHandler wxCpSubscribeHandler, WxCpProperties properties, WxCpContactChangeHandler wxCpContactChangeHandler,
                             WxCpEnterAgentHandler wxCpEnterAgentHandler,WxCpMenuClickHandler wxCpMenuClickHandler,WxCpScanHandler wxCpScanHandler) {
        this.wxCpLogHandler = wxCpLogHandler;
        this.wxCpNullHandler = wxCpNullHandler;
        this.wxCpLocationHandler = wxCpLocationHandler;
        this.wxCpMenuHandler = wxCpMenuHandler;
        this.wxCpMsgHandler = wxCpMsgHandler;
        this.wxCpUnsubscribeHandler = wxCpUnsubscribeHandler;
        this.wxCpSubscribeHandler = wxCpSubscribeHandler;
        this.properties = properties;
        this.wxCpContactChangeHandler = wxCpContactChangeHandler;
        this.wxCpEnterAgentHandler = wxCpEnterAgentHandler;
        this.wxCpMenuClickHandler=wxCpMenuClickHandler;
        this.wxCpScanHandler=wxCpScanHandler;
    }


    public static Map<Integer, WxCpMessageRouter> getRouters() {
        return routers;
    }

    /**
     * 默认获取第一个配置Agent应用的服务
     *
     * @return {@link WxCpService}
     */
    public static WxCpService getCpService() {
        if (CollUtil.isEmpty(appConfigList)) {
            return null;
        }
        return getCpService(appConfigList.get(0).getAgentId());
    }

    /**
     * 获取某应用的服务
     *
     * @param agentId 应用ID
     * @return {@link WxCpService}
     */
    public static WxCpService getCpService(Integer agentId) {
        if (!cpServices.keySet().contains(agentId)) {
            return null;
        }
        return cpServices.get(agentId);
    }

    @PostConstruct
    public void initServices() {
        log.debug("【微信组件】: 开启 <企业微信> 相关的配置");
        if (CollUtil.isEmpty(this.properties.getAppConfigs())) {
            log.warn("【微信组件】: 请在配置文件中添加企业微信相关配置！参数以yf.wechat.cp开头。");
            return;
        }
        appConfigList = this.properties.getAppConfigs();

        cpServices = this.properties.getAppConfigs().stream().map(a -> {
            val configStorage = new WxCpDefaultConfigImpl();
            configStorage.setCorpId(this.properties.getCorpId());
            configStorage.setAgentId(a.getAgentId());
            configStorage.setCorpSecret(a.getSecret());
            configStorage.setToken(a.getToken());
            configStorage.setAesKey(a.getAesKey());
            val service = new WxCpServiceImpl();
            service.setWxCpConfigStorage(configStorage);
            routers.put(a.getAgentId(), this.newRouter(service));
            return service;
        }).collect(Collectors.toMap(service -> service.getWxCpConfigStorage().getAgentId(), a -> a));
    }

    private WxCpMessageRouter newRouter(WxCpService wxCpService) {
        final val newRouter = new WxCpMessageRouter(wxCpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.wxCpLogHandler).next();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(this.wxCpMenuHandler).end();

        // 点击菜单链接事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW).handler(this.wxCpMenuClickHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE).handler(this.wxCpSubscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(this.wxCpUnsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.LOCATION).handler(this.wxCpLocationHandler)
                .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION)
                .handler(this.wxCpLocationHandler).end();

        // 扫码事件（这里使用了一个空的处理器，可以根据自己需要进行扩展）
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCAN).handler(this.wxCpScanHandler).end();

        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.CHANGE_CONTACT).handler(this.wxCpContactChangeHandler).end();

        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.ENTER_AGENT).handler(this.wxCpEnterAgentHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.wxCpMsgHandler).end();

        return newRouter;
    }
}
