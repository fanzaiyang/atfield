/**
 *
 */
package cn.fanzy.atfield.upload.v2.property;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;


/**
 * MinIO属性配置
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "atfield.upload.v2")
public class AttachUploadProperty implements Serializable {
    @Serial
    private static final long serialVersionUID = -5609231397331602609L;

    private Local local;

    /**
     * 当地
     *
     * @author fanzaiyang
     * @date 2025/04/25
     */
    @Data
    public static class Local implements Serializable {
        @Serial
        private static final long serialVersionUID = -8007573709460818369L;
        /**
         * 是否启用controller接口，默认：true开启
         */
        private Boolean enable;

        /**
         * 基础目录
         */
        private String baseDir;

    }
}
