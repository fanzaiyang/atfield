package cn.fanzy.breeze.admin.module.auth.config;

import cn.fanzy.breeze.admin.module.auth.controller.BreezeAdminAuthController;
import cn.fanzy.breeze.admin.module.auth.service.BreezeAdminAuthService;
import cn.fanzy.breeze.admin.module.auth.service.impl.BreezeAdminAuthServiceImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.safe.service.BreezeSafeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(BreezeAdminAuthController.class)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-auth"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminAuthConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminAuthService breezeAdminAuthService(SqlToyHelperDao sqlToyHelperDao, BreezeSafeService breezeSafeService) {
        return new BreezeAdminAuthServiceImpl(sqlToyHelperDao, breezeSafeService);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Auth配置> 相关的配置。");
    }
}