package cn.fanzy.breeze.admin.module.system.config;

import cn.fanzy.breeze.admin.module.system.account.config.BreezeAdminAccountConfig;
import cn.fanzy.breeze.admin.module.system.corp.config.BreezeAdminOrgConfig;
import cn.fanzy.breeze.admin.module.system.menu.config.BreezeAdminMenuConfig;
import cn.fanzy.breeze.admin.module.system.roles.config.BreezeAdminRoleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@ImportAutoConfiguration({BreezeAdminAccountConfig.class, BreezeAdminRoleConfig.class
        , BreezeAdminMenuConfig.class, BreezeAdminOrgConfig.class})
@Slf4j
@Configuration
public class BreezeAdminSystemConfig {
}
