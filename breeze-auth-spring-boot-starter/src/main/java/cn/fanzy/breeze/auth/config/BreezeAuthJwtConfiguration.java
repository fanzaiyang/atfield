package cn.fanzy.breeze.auth.config;

import cn.dev33.satoken.jwt.StpLogicJwtForMixin;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import cn.fanzy.breeze.auth.properties.BreezeAuthProperties;
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
@EnableConfigurationProperties(BreezeAuthProperties.class)
@ConditionalOnClass(StpLogicJwtForSimple.class)
@ConditionalOnProperty(prefix = "breeze.auth.jwt", name = {"enable"}, havingValue = "true")
public class BreezeAuthJwtConfiguration {

    private final BreezeAuthProperties properties;

    @Bean
    public StpLogic getStpLogicJwtSimple() {
        BreezeAuthProperties.Jwt.JwtMode jwtMode = properties.getJwt().getMode();
        if (jwtMode.equals(BreezeAuthProperties.Jwt.JwtMode.simple)) {
            log.info("Simple 模式：Token 风格替换");
            return new StpLogicJwtForSimple();
        }
        if (jwtMode.equals(BreezeAuthProperties.Jwt.JwtMode.mixin)) {
            log.info("Mixin 模式：混入部分逻辑");
            return new StpLogicJwtForMixin();
        }
        if (jwtMode.equals(BreezeAuthProperties.Jwt.JwtMode.stateless)) {
            log.info("Stateless 模式：服务器完全无状态");
            return new StpLogicJwtForStateless();
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