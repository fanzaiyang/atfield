package cn.fanzy.breeze.admin.module.system.account.config;

import cn.fanzy.breeze.admin.module.system.account.controller.BreezeAdminAccountController;
import cn.fanzy.breeze.admin.module.system.account.service.BreezeAdminAccountService;
import cn.fanzy.breeze.admin.module.system.account.service.BreezeAdminAccountServiceImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(BreezeAdminAccountController.class)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-account"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminAccountConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminAccountService breezeAdminAccountService(SqlToyHelperDao sqlToyHelperDao) {
        return new BreezeAdminAccountServiceImpl(sqlToyHelperDao);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Account配置> 相关的配置。");
    }
}