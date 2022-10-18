package cn.fanzy.breeze.admin.module.system.corp.config;

import cn.fanzy.breeze.admin.module.system.corp.controller.BreezeAdminOrgController;
import cn.fanzy.breeze.admin.module.system.corp.service.BreezeAdminOrgService;
import cn.fanzy.breeze.admin.module.system.corp.service.impl.BreezeAdminOrgServiceImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(BreezeAdminOrgController.class)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-org"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminOrgConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminOrgService breezeAdminOrgService(SqlToyHelperDao sqlToyHelperDao) {
        return new BreezeAdminOrgServiceImpl(sqlToyHelperDao);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Corp> 相关的配置。");
    }
}