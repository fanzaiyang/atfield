package cn.fanzy.breeze.minio.config;

import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.minio.service.impl.BreezeMultipartFileServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({BreezeMinIOProperties.class})
@ConditionalOnClass(JdbcTemplate.class)
public class BreezeMinioMultipartConfig {
    private final BreezeMinIOProperties properties;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public BreezeMultipartFileService breezeMultipartFileService() {
        return new BreezeMultipartFileServiceImpl(jdbcTemplate, properties);
    }
    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <MinIO分片上传> 相关的配置。");
    }
}
