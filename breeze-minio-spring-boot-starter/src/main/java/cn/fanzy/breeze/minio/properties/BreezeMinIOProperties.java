/**
 *
 */
package cn.fanzy.breeze.minio.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * MinIO属性配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "breeze.minio")
public class BreezeMinIOProperties {
    /**
     * MinIO服务集合
     */
    private Map<String,MinioServerConfig> servers = new LinkedHashMap<>();

    @Data
    public static class MinioServerConfig {
        /**
         * 公网MinIO地址，URL to S3 service.
         */
        private String endpoint;

        /**
         * Access key (aka user ID) of an account in the S3 service.
         */
        private String accessKey;
        /**
         * Secret key (aka password) of an account in the S3 service.
         */
        private String secretKey;

        /**
         * 默认的存储桶
         */
        private String bucket;
        /**
         * 内网地址，空则使用公网地址
         */
        private String innerEndpoint;

        public String getInnerEndpoint() {
            if (innerEndpoint == null || innerEndpoint.isEmpty()) {
                log.warn("未配置内网地址，使用公网地址！");
                return endpoint;
            }
            return innerEndpoint;
        }
    }

}
