package cn.fanzy.breeze.wechat.ma;

import cn.fanzy.breeze.wechat.ma.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WxMaBeanAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WxMaLogHandler wxMaLogHandler() {
        return (message, context, service, sessionManager) -> {
            log.info("收到消息：" + message.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaImageHandler wxMaImageHandler() {
        return (message, context, service, sessionManager) -> {
            log.info("图片消息：" + message.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaQrCodeHandler wxMaQrCodeHandler() {
        return (message, context, service, sessionManager) -> {
            log.info("二维码消息：" + message.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaSubscribeHandler wxMaSubscribeHandler() {
        return (message, context, service, sessionManager) -> {
            log.info("订阅消息：" + message.toString());
            return null;
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaTextHandler wxMaTextHandler() {
        return (message, context, service, sessionManager) -> {
            log.info("文本消息：" + message.toString());
            return null;
        };
    }
}
