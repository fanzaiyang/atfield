package cn.fanzy.breeze.minio.config;

import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.minio.service.impl.BreezeMultipartFileServiceImpl;
import cn.fanzy.breeze.web.swagger.properties.BreezeSwaggerProperties;
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
    private final BreezeSwaggerProperties breezeSwaggerProperties;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public BreezeMultipartFileService breezeMultipartFileService() {
        return new BreezeMultipartFileServiceImpl(jdbcTemplate, properties);
    }

//    @Bean
//    @ConditionalOnMissingBean(name = "breezeDefaultApi")
//    @ConditionalOnProperty(prefix = "breeze.web.swagger", name = {"packages-to-scan"})
//    public GroupedOpenApi breezeDefaultApi() {
//        List<String> packagesToScan = breezeSwaggerProperties.getPackagesToScan();
//        if (packagesToScan == null) {
//            packagesToScan = CollUtil.newArrayList("cn.fanzy.breeze.minio.controller");
//        } else {
//            packagesToScan.add("cn.fanzy.breeze.minio.controller");
//        }
//        return GroupedOpenApi.builder()
//                .group("-默认分组-")
//                .pathsToMatch("/**")
//                .packagesToScan(packagesToScan.toArray(new String[packagesToScan.size()]))
//                .build();
//    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <MinIO分片上传> 相关的配置。");
    }
}
