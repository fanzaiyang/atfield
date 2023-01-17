package cn.fanzy.breeze.admin.module.system.attachments.config;

import cn.fanzy.breeze.admin.module.system.attachments.controller.BreezeAdminAttachmentController;
import cn.fanzy.breeze.admin.module.system.attachments.service.BreezeAdminAttachmentService;
import cn.fanzy.breeze.admin.module.system.attachments.service.impl.BreezeAdminAttachmentServiceImpl;
import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 微风管理附件配置
 *
 * @author fanzaiyang
 * @since 2022-11-01
 */
@ImportAutoConfiguration(BreezeAdminAttachmentController.class)
@Slf4j
@Configuration
@ConditionalOnClass(BreezeMinioConfiguration.class)
@ConditionalOnProperty(prefix = "breeze.admin.module", name = {"enable-attachment"}, havingValue = "true", matchIfMissing = true)
public class BreezeAdminAttachmentConfig {

    @Bean
    @ConditionalOnMissingBean
    public BreezeAdminAttachmentService breezeAdminAttachmentService(SqlToyHelperDao sqlToyHelperDao, BreezeMultipartFileService breezeMultipartFileService) {
        return new BreezeAdminAttachmentServiceImpl(sqlToyHelperDao,breezeMultipartFileService);
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Admin-Attachment> 相关的配置。");
    }
}