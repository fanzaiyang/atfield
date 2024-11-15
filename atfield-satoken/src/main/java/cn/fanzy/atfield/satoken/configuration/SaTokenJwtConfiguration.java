package cn.fanzy.atfield.satoken.configuration;

import cn.dev33.satoken.jwt.StpLogicJwtForMixin;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import cn.fanzy.atfield.satoken.enums.SaTokenJwtEnum;
import cn.fanzy.atfield.satoken.property.SaTokenExtraProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SA 令牌路由配置
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(SaTokenExtraProperty.class)
@ConditionalOnProperty(prefix = "atfield.sa-token", name = {"jwtEnable"}, havingValue = "true")
public class SaTokenJwtConfiguration implements WebMvcConfigurer {
    private final SaTokenExtraProperty property;

    @Bean
    @ConditionalOnMissingBean
    public StpLogic getStpLogicJwt() {
        if (SaTokenJwtEnum.stateless.equals(property.getJwtType())) {
            // Sa-Token 整合 jwt (Simple 简单模式)
            return new StpLogicJwtForStateless();
        }
        if (SaTokenJwtEnum.mixin.equals(property.getJwtType())) {
            // Sa-Token 整合 jwt (Simple 简单模式)
            return new StpLogicJwtForMixin();
        }
        if (SaTokenJwtEnum.simple.equals(property.getJwtType())) {
            // Sa-Token 整合 jwt (Simple 简单模式)
            return new StpLogicJwtForSimple();
        }
        throw new RuntimeException("无效的jwt类型,请检查：atfield.sa-token.jwt-type");
    }
}
