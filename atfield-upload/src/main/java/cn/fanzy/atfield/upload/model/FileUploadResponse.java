package cn.fanzy.atfield.upload.model;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 响应
 *
 * @author fanzaiyang
 * @date 2023/12/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -4633681275028052107L;
    /**
     * 文件在数据库中的ID
     */
    private String id;
    /**
     * 标识
     */
    private String etag;
    /**
     * MinIO地址
     */
    private String endpoint;
    /**
     * 存储桶
     */
    private String bucket;
    /**
     * 对象名
     */
    private String objectName;

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小，单位：Mb
     */
    private double fileMbSize;

    /**
     * 文件字节大小，单位：b
     * {@linkplain inputStream.available()}
     */
    private double fileByteSize;

    /**
     * 临时预览地址
     */
    private String previewUrl;
    /**
     * 预览公共URL
     * <pre>需要设置公开可读权限。</pre>
     */
    private String previewPublicUrl;

    public String getPreviewPublicUrl() {
        return StrUtil.subBefore(previewUrl, "?", false);
    }
}
