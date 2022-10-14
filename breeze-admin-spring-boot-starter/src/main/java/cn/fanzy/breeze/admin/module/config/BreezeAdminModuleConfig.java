package cn.fanzy.breeze.admin.module.config;

import cn.fanzy.breeze.admin.module.system.config.BreezeAdminSystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@ImportAutoConfiguration({BreezeAdminSystemConfig.class})
@Slf4j
@Configuration
public class BreezeAdminModuleConfig {

}
