package cn.fanzy.atfield.upload.configuration;

import cn.fanzy.atfield.upload.property.UploadProperty;
import cn.fanzy.atfield.upload.service.UploadPartService;
import cn.fanzy.atfield.upload.service.impl.UploadPartServiceImpl;
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
 * 分片上传
 *
 * @author fanzaiyang
 * @date 2023-07-03
 */
@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({UploadProperty.class})
@ConditionalOnClass(JdbcTemplate.class)
public class UploadPartConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UploadPartService filePartUploadService(JdbcTemplate jdbcTemplate,
                                                   UploadProperty properties) {
        return new UploadPartServiceImpl(jdbcTemplate, properties);
    }

    @PostConstruct
    public void init() {
        log.info("开启 <MinIO分片上传> 相关的配置。");
    }
}
