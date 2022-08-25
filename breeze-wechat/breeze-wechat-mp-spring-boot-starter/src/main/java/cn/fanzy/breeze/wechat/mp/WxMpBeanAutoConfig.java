package cn.fanzy.breeze.wechat.mp;

import cn.fanzy.breeze.wechat.mp.handler.*;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * wx议员bean自动配置
 *
 * @author fanzaiyang
 * @date 2022/03/07
 */
@Slf4j
@Configuration
public class WxMpBeanAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WxMpLogHandler wxMpLogHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("接收到请求消息，内容：{}", JSONUtil.toJsonStr(wxMessage));
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpNullHandler wxMpNullHandler() {
        return (wxMessage, context, cpService, sessionManager) -> null;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpLocationHandler wxMpLocationHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("上报地理位置，纬度 : {}\n经度 : {}\n精度 : {}",
                    wxMessage.getLatitude(), wxMessage.getLongitude(), String.valueOf(wxMessage.getPrecision()));
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpMenuHandler wxMpMenuHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("菜单，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpMsgHandler wxMpMsgHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("收到消息，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpUnsubscribeHandler wxMpUnsubscribeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("取消关注用户 OPENID: " + wxMessage.getFromUser());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpSubscribeHandler wxMpSubscribeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("感谢关注 OPENID: " + wxMessage.getFromUser());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpKfSessionHandler wxMpKfSessionHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("对会话做处理" + wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpScanHandler wxMpScanHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("扫码事件处理" + wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpStoreCheckNotifyHandler wxMpStoreCheckNotifyHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("处理门店审核事件" + wxMessage.toString());
            return null;
        };
    }
}
