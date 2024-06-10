package cn.fanzy.atfield.wechat.cp;

import cn.fanzy.atfield.wechat.cp.handler.*;
import cn.fanzy.atfield.wechat.cp.properties.WxCpProperties;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WX CP Bean 自动配置
 *
 * @author fanzaiyang
 * @date 2024/05/27
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxCpProperties.class)
public class WxCpBeanAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WxCpLogHandler wxCpLogHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpLogHandler接收到请求消息，内容：{}", JSONUtil.toJsonStr(wxMessage));
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpNullHandler wxCpNullHandler() {
        return (wxMessage, context, cpService, sessionManager) -> null;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpApproveHandler wxCpApproveHandler() {
        return (wxMessage, context, cpService, sessionManager) -> null;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpLocationHandler wxCpLocationHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpLocationHandler上报地理位置，纬度 : {}\n经度 : {}\n精度 : {}",
                    wxMessage.getLatitude(), wxMessage.getLongitude(), String.valueOf(wxMessage.getPrecision()));
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpMenuHandler wxCpMenuHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpMenuHandler菜单，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpMenuClickHandler wxCpMenuClickHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpMenuClickHandler菜单点击，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpMsgHandler wxCpMsgHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpMsgHandler收到消息，{}", wxMessage.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpUnsubscribeHandler wxCpUnsubscribeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpUnsubscribeHandler取消关注用户 OPENID: " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpSubscribeHandler wxCpSubscribeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpSubscribeHandler感谢关注 OPENID: " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpEnterAgentHandler wxCpEnterAgentHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpEnterAgentHandler输入代理处理程序 " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpContactChangeHandler wxCpContactChangeHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpContactChangeHandler " + wxMessage.getFromUserName());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpScanHandler wxCpScanHandler() {
        return (wxMessage, context, cpService, sessionManager) -> {
            log.info("WxCpScanHandler " + wxMessage.getFromUserName());
            return null;
        };
    }
}
