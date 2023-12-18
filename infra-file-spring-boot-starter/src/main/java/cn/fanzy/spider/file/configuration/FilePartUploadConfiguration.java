package cn.fanzy.spider.file.configuration;

import cn.fanzy.spider.file.property.FileUploadProperty;
import cn.fanzy.spider.file.service.FilePartUploadService;
import cn.fanzy.spider.file.service.impl.FilePartUploadServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 微风minio多部分配置
 *
 * @author fanzaiyang
 * @date 2023-07-03
 */
@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({FileUploadProperty.class})
@ConditionalOnClass(JdbcTemplate.class)
public class FilePartUploadConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FilePartUploadService filePartUploadService(JdbcTemplate jdbcTemplate,
                                                       FileUploadProperty properties) {
        return new FilePartUploadServiceImpl(jdbcTemplate, properties);
    }

    @PostConstruct
    public void init() {
        log.info("开启 <MinIO分片上传> 相关的配置。");
    }
}
