package cn.fanzy.breeze.wechat.cp;

import cn.fanzy.breeze.wechat.cp.handler.*;
import cn.fanzy.breeze.wechat.cp.properties.WxCpProperties;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(WxCpProperties.class)
public class WxCpBeanAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WxCpLogHandler logHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("接收到请求消息，内容：{}", JSONUtil.toJsonStr(wxMessage));
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpNullHandler nullHandler() {
        return (wxMessage, context, cpService, sessionManager) -> null;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpLocationHandler locationHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("上报地理位置，纬度 : {}\n经度 : {}\n精度 : {}",
                    wxMessage.getLatitude(), wxMessage.getLongitude(), String.valueOf(wxMessage.getPrecision()));
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpMenuHandler menuHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("菜单，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpMsgHandler msgHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("收到消息，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpUnsubscribeHandler unsubscribeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("取消关注用户 OPENID: " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpSubscribeHandler subscribeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("感谢关注 OPENID: " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpEnterAgentHandler enterAgentHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("输入代理处理程序 " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpContactChangeHandler contactChangeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("ContactChangeHandler " + wxMessage.getFromUserName());
            return null;
        };
    }
}
