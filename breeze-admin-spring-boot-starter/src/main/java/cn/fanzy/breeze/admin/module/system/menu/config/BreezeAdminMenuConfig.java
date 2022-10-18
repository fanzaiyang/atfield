package cn.fanzy.breeze.admin.module.system.menu.config;

import cn.fanzy.breeze.admin.module.system.menu.controller.BreezeAdminMenuController;
import cn.fanzy.breeze.admin.module.system.menu.service.BreezeAdminMenuService;
import cn.fanzy.breeze.admin.module.system.menu.service.impl.BreezeAdminMenuServiceImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(BreezeAdminMenuController.class)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-menu"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminMenuConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminMenuService breezeAdminMenuService(SqlToyHelperDao sqlToyHelperDao) {
        return new BreezeAdminMenuServiceImpl(sqlToyHelperDao);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Menu> 相关的配置。");
    }
}