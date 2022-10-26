package cn.fanzy.breeze.admin.module.system.dict.config;

import cn.fanzy.breeze.admin.module.system.dict.controller.BreezeAdminDictController;
import cn.fanzy.breeze.admin.module.system.dict.service.BreezeAdminDictService;
import cn.fanzy.breeze.admin.module.system.dict.service.impl.BreezeAdminDictServiceImpl;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ImportAutoConfiguration(BreezeAdminDictController.class)
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-dict"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminDictConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminDictService breezeAdminDictService(SqlToyHelperDao sqlToyHelperDao) {
        return new BreezeAdminDictServiceImpl(sqlToyHelperDao);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Dict> 相关的配置。");
    }
}