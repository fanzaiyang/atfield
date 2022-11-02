package cn.fanzy.breeze.admin.module.system.config;

import cn.fanzy.breeze.admin.module.system.account.config.BreezeAdminAccountConfig;
import cn.fanzy.breeze.admin.module.system.attachments.config.BreezeAdminAttachmentConfig;
import cn.fanzy.breeze.admin.module.system.corp.config.BreezeAdminOrgConfig;
import cn.fanzy.breeze.admin.module.system.dict.config.BreezeAdminDictConfig;
import cn.fanzy.breeze.admin.module.system.menu.config.BreezeAdminMenuConfig;
import cn.fanzy.breeze.admin.module.system.roles.config.BreezeAdminRoleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 微风管理系统配置
 *
 * @author fanzaiyang
 * @date 2022-11-01
 */
@ImportAutoConfiguration({BreezeAdminAccountConfig.class, BreezeAdminRoleConfig.class
        , BreezeAdminMenuConfig.class, BreezeAdminOrgConfig.class, BreezeAdminDictConfig.class,
        BreezeAdminAttachmentConfig.class})
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-system"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminSystemConfig {
}
