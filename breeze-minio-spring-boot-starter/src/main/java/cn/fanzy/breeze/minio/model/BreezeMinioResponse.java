package cn.fanzy.breeze.minio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeMinioResponse {
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
     * 临时预览地址
     */
    private String previewUrl;

}
