package cn.fanzy.breeze.auth.jwt.config;

import cn.dev33.satoken.jwt.StpLogicJwtForMixin;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import cn.fanzy.breeze.auth.jwt.BreezeJwtForStateless;
import cn.fanzy.breeze.auth.jwt.properties.BreezeAuthJwtProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(BreezeAuthJwtProperties.class)
@ConditionalOnClass(StpLogicJwtForSimple.class)
@ConditionalOnProperty(prefix = "breeze.auth.jwt", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeAuthJwtConfiguration {

    private final BreezeAuthJwtProperties properties;

    @Bean
    public StpLogic getStpLogicJwtSimple() {
        BreezeAuthJwtProperties.JwtMode jwtMode = properties.getMode();
        if (jwtMode.equals(BreezeAuthJwtProperties.JwtMode.simple)) {
            log.info("Simple 模式：Token 风格替换");
            return new StpLogicJwtForSimple();
        }
        if (jwtMode.equals(BreezeAuthJwtProperties.JwtMode.mixin)) {
            log.info("Mixin 模式：混入部分逻辑");
            return new StpLogicJwtForMixin();
        }
        if (jwtMode.equals(BreezeAuthJwtProperties.JwtMode.stateless)) {
            log.info("Stateless 模式：服务器完全无状态");
            return new StpLogicJwtForStateless();
        }
        if (jwtMode.equals(BreezeAuthJwtProperties.JwtMode.statelessNotCheck)) {
            log.info("Stateless NotCheck模式：服务器完全无状态(不检查token是否过期)");
            return new BreezeJwtForStateless();
        }
        throw new RuntimeException("未知的jwt类型，请检查sa-token.jwt-mode,必须是simple或mixin或stateless。");
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <注册SaToken JWT> 相关的配置。");
    }

}