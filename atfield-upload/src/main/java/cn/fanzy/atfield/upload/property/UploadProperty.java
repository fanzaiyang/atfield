/**
 *
 */
package cn.fanzy.atfield.upload.property;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * MinIO属性配置
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "atfield.file")
public class UploadProperty implements Serializable {
    @Serial
    private static final long serialVersionUID = -5609231397331602609L;
    /**
     * MinIO服务集合
     */
    private Map<String, MinioServerConfig> servers = new LinkedHashMap<>();

    private MinioApi api = new MinioApi();

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

    @Data
    public static class MinioApi {
        /**
         * 是否启用controller接口，默认：true开启
         */
        private Boolean enable;
        /**
         * 上传初始化接口名称，默认：/breeze/minio/multipart/init
         */
        private String init;
        /**
         * 上传合并接口名称，默认：/breeze/minio/multipart/merge
         */
        private String merge;
        /**
         * 获取某分片上传地址，默认：/breeze/minio/multipart/presigned
         */
        private String presigned;

        private String tableName;


        public String getTableName() {
            return StrUtil.blankToDefault(tableName, "sys_file_info");
        }
    }
}
