package cn.fanzy.breeze.admin.module.system.roles.config;

import cn.fanzy.breeze.admin.module.system.roles.controller.BreezeAdminSysRoleController;
import cn.fanzy.breeze.admin.module.system.roles.service.BreezeAdminRoleService;
import cn.fanzy.breeze.admin.module.system.roles.service.impl.BreezeAdminRoleServiceImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(BreezeAdminSysRoleController.class)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-role"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminRoleConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminRoleService breezeAdminRoleService(SqlToyHelperDao sqlToyHelperDao) {
        return new BreezeAdminRoleServiceImpl(sqlToyHelperDao);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Role> 相关的配置。");
    }
}