package cn.fanzy.breeze.admin.module.system.account.config;

import cn.fanzy.breeze.admin.module.system.account.controller.BreezeAdminAccountController;
import cn.fanzy.breeze.admin.module.system.account.service.BreezeAdminAccountService;
import cn.fanzy.breeze.admin.module.system.account.service.BreezeAdminAccountServiceImpl;
import cn.fanzy.breeze.admin.properties.BreezeAdminProperties;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 微风管理员帐户配置
 *
 * @author fanzaiyang
 * @since 2022-11-07
 */
@ImportAutoConfiguration(BreezeAdminAccountController.class)
@Slf4j
@Configuration
@EnableConfigurationProperties(BreezeAdminProperties.class)
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-account"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminAccountConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminAccountService breezeAdminAccountService(SqlToyHelperDao sqlToyHelperDao, BreezeAdminProperties properties) {
        return new BreezeAdminAccountServiceImpl(sqlToyHelperDao, properties);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Account配置> 相关的配置。");
    }
}