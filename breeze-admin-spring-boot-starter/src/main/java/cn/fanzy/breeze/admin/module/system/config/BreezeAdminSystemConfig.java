package cn.fanzy.breeze.admin.module.system.config;

import cn.fanzy.breeze.admin.module.system.account.config.BreezeAdminAccountConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@ImportAutoConfiguration({BreezeAdminAccountConfig.class})
@Slf4j
@Configuration
public class BreezeAdminSystemConfig {
}
